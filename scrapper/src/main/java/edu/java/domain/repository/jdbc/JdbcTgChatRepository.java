package edu.java.domain.repository.jdbc;

import edu.java.domain.dto.ChatDTO;
import edu.java.domain.repository.TgChatRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

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
        return jdbcTemplate.query("select chat_id, created_at from chat",
                (resultSet, rowNum) -> convertToChatDTO(resultSet));
    }

    @Override
    public Optional<ChatDTO> findById(Long chatId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select chat_id, created_at from chat where chat_id = ?",
                (rs, rowNum) -> convertToChatDTO(rs)));
    }

    private ChatDTO convertToChatDTO(ResultSet resultSet) throws SQLException {
        return new ChatDTO(
                resultSet.getLong("chat_id"),
                resultSet.getTimestamp("created_at")
                        .toInstant().atZone(ZoneOffset.systemDefault()).toOffsetDateTime());
    }
}
