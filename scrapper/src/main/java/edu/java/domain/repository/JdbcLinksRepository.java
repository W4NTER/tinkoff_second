package edu.java.domain.repository;

import edu.java.controller.dto.request.AddLinkRequest;
import edu.java.domain.dto.LinksDTO;
import java.net.URI;
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
    public void add(AddLinkRequest link, Long chatId) {
        jdbcTemplate.update("insert into links (link, chat_id, created_at, edited_at) values (?, ?, ?, ?)",
                link.link().toString(), chatId, OffsetDateTime.now(), OffsetDateTime.now());
    }

    @Override
    @Transactional
    public void remove(Long id) {
        jdbcTemplate.update("delete from links where link_id = ?", id);
    }

    @Override
    @Transactional
    public List<LinksDTO> findAll() {
        return jdbcTemplate.query("select link_id, link, chat_id, created_at, edited_at from links",
                (resultSet, rowNum) -> {
                    return new LinksDTO(
                    resultSet.getLong("link_id"),
                    URI.create(resultSet.getString("link")),
                    resultSet.getLong("chat_id"),
                    resultSet.getTimestamp("created_at")
                            .toInstant().atZone(ZoneOffset.systemDefault()).toOffsetDateTime(),
                    resultSet.getTimestamp("edited_at")
                            .toInstant().atZone(ZoneOffset.systemDefault()).toOffsetDateTime());
                });
    }
}
