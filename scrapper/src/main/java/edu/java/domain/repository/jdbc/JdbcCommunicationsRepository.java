package edu.java.domain.repository.jdbc;

import edu.java.domain.dto.CommunicationsDTO;
import edu.java.domain.repository.CommunicationsRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

public class JdbcCommunicationsRepository implements CommunicationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcCommunicationsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void add(Long chatId, Long linkId) {
        jdbcTemplate.update("insert into communications values (?, ?)", chatId, linkId);
    }

    @Override
    @Transactional
    public void remove(Long chatId, Long linkId) {
        jdbcTemplate.update("delete from communications where chat_id = ? and link_id = ?", chatId, linkId);
    }

    @Override
    @Transactional
    public List<CommunicationsDTO> findAll() {
        return jdbcTemplate.query("select chat_id, link_id from communications",
                (resultSet, rowNum) -> createDTOObject(resultSet));
    }

    @Override
    public List<Long> findChatsByLink(long linkId) {
        return jdbcTemplate.query("select chat_id, link_id from communications where link_id = ?",
                (rs, rowNum) -> createDTOObject(rs).chatId(), linkId);
    }


    private CommunicationsDTO createDTOObject(ResultSet resultSet) throws SQLException {
        return new CommunicationsDTO(
            resultSet.getLong("chat_id"),
            resultSet.getLong("link_id"));
    }
}
