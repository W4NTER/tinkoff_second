package edu.java.domain.repository.jdbc;

import edu.java.domain.dto.ChatDTO;
import edu.java.domain.repository.TgChatRepository;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcTgChatRepository implements TgChatRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTgChatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void add(Long chatId) {
        jdbcTemplate.update("insert into chat (chat_id, created_at) values (?, ?)",
                chatId, OffsetDateTime.now());
    }

    @Override
    @Transactional
    public void remove(Long id) {
        jdbcTemplate.update("DELETE from chat where chat_id = ?", id);
    }

    @Override
    @Transactional
    public List<ChatDTO> findAll() {
        return jdbcTemplate.query("select chat_id, created_at from chat", (resultSet, rowNum) -> {
            return new ChatDTO(
                    resultSet.getLong("chat_id"),
                    resultSet.getTimestamp("created_at")
                            .toInstant().atZone(ZoneOffset.systemDefault()).toOffsetDateTime());
        });
    }
}
