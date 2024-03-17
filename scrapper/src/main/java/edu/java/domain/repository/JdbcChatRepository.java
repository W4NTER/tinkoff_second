package edu.java.domain.repository;

import edu.java.domain.dto.ChatDTO;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcChatRepository implements ChatRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcChatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void add() {
        jdbcTemplate.update("insert into chat (created_at, edited_at) values (?, ?)",
                OffsetDateTime.now(), OffsetDateTime.now());
    }

    @Override
    @Transactional
    public void remove(Long id) {
        jdbcTemplate.update("DELETE from chat where chat_id = ?", id);
    }

    @Override
    @Transactional
    public List<ChatDTO> findAll() {
        return jdbcTemplate.query("select chat_id, created_at, edited_at from chat", (resultSet, rowNum) -> {
            return new ChatDTO(
                    resultSet.getLong("chat_id"),
                    resultSet.getTimestamp("created_at")
                            .toInstant().atZone(ZoneOffset.systemDefault()).toOffsetDateTime(),
                    resultSet.getTimestamp("edited_at")
                            .toInstant().atZone(ZoneOffset.systemDefault()).toOffsetDateTime());
        });
    }
}
