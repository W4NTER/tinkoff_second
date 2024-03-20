package edu.java.domain.repository.jdbc;

import edu.java.domain.dto.LinksDTO;
import edu.java.domain.repository.LInksRepository;
import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class JdbcLinksRepository implements LInksRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcLinksRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void add(URI link, Long chatId) {
        var timeNow = OffsetDateTime.now();
        jdbcTemplate.update("insert into links (link, chat_id, created_at, edited_at) values (?, ?, ?, ?)",
                link.toString(), chatId, timeNow, timeNow);
    }

    @Override
    @Transactional
    public void remove(URI link, Long chatId) {
        jdbcTemplate.update("delete from links where link = ? and chat_id = ?", link.toString(), chatId);
    }

    @Override
    @Transactional
    public List<LinksDTO> findAll(Long thChatId) {
        return jdbcTemplate.query("select link_id, link, chat_id, created_at, edited_at from links where chat_id = ?",
                (resultSet, rowNum) -> createLinksDTO(resultSet), thChatId);
    }

    public LinksDTO getLink(URI url, Long chatId) {
        return jdbcTemplate.queryForObject(
                "select link_id, link, chat_id, created_at, edited_at from links where chat_id = ? and link = ?",
                (resultSet, rowNum) -> createLinksDTO(resultSet), chatId, url.toString());
    }

    private LinksDTO createLinksDTO(ResultSet resultSet) throws SQLException {
        return new LinksDTO(
                resultSet.getLong("link_id"),
                URI.create(resultSet.getString("link")),
                resultSet.getLong("chat_id"),
                resultSet.getTimestamp("created_at")
                        .toInstant().atZone(ZoneOffset.systemDefault())
                        .toOffsetDateTime(),
                resultSet.getTimestamp("edited_at")
                        .toInstant().atZone(ZoneOffset.systemDefault())
                        .toOffsetDateTime());
    }
}
