package edu.java.domain.repository.jdbc;

import edu.java.domain.dto.LinksDTO;
import edu.java.domain.repository.LinksRepository;
import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;



public class JdbcLinksRepository implements LinksRepository {
    private final JdbcTemplate jdbcTemplate;
    private final static int TIME_IN_MINUTES_TO_OUTDATED_LINK = 5;

    public JdbcLinksRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void add(URI link, OffsetDateTime lastUpdate) {
        var timeNow = OffsetDateTime.now();
        int rowUpdate = jdbcTemplate.update(
                "update links set last_update = ?, last_check = ? where link = ?",
                lastUpdate, timeNow, link.toString());
        if (rowUpdate == 0) {
            jdbcTemplate.update(
                    "insert into links (link, created_at, last_update, last_check) VALUES (?, ?, ?, ?)",
                    link.toString(), timeNow, lastUpdate, timeNow);
        }
    }

    @Override
    @Transactional
    public void remove(URI link) {
        jdbcTemplate.update(
                "delete from links where link = ?", link.toString());
    }

    @Override
    @Transactional
    public List<LinksDTO> findAll() {
        return jdbcTemplate.query("select link_id, link, created_at, last_update, last_check from links",
                (resultSet, rowNum) -> createLinksDTO(resultSet));
    }

    @Override
    @Transactional
    public List<LinksDTO> findAllOutdatedLinks() {
        OffsetDateTime thresholdTime = OffsetDateTime.now().minusMinutes(TIME_IN_MINUTES_TO_OUTDATED_LINK);
        return jdbcTemplate.query(
                "select link_id, link, created_at, last_update, last_check from links where last_check <= ?",
                (resultSet, rowNum) -> createLinksDTO(resultSet), thresholdTime);
    }

    @Transactional
    public LinksDTO getLink(URI url) {
        //поменял int на long могло что-то сломаться
        long linkId = jdbcTemplate.queryForObject(
                "select link_id from links where link = ?",
                Long.class, url.toString()).longValue();
        return jdbcTemplate.queryForObject(
                "select link_id, link, created_at, last_update, last_check from links where link_id = ?",
                (resultSet, rowNum) -> createLinksDTO(resultSet), linkId);
    }

    private LinksDTO createLinksDTO(ResultSet resultSet) throws SQLException {
        return new LinksDTO(
                resultSet.getLong("link_id"),
                URI.create(resultSet.getString("link")),
                resultSet.getTimestamp("created_at")
                        .toInstant().atZone(ZoneOffset.systemDefault())
                        .toOffsetDateTime(),
                resultSet.getTimestamp("last_update")
                        .toInstant().atZone(ZoneOffset.systemDefault())
                        .toOffsetDateTime(),
                resultSet.getTimestamp("last_check")
                        .toInstant().atZone(ZoneOffset.systemDefault())
                        .toOffsetDateTime());
    }
}
