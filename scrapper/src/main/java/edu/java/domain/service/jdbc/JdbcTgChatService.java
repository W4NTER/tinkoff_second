package edu.java.domain.service.jdbc;

import edu.java.domain.repository.jdbc.JdbcTgChatRepository;
import edu.java.domain.service.TgChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcTgChatService implements TgChatService {
    @Autowired
    private JdbcTgChatRepository jdbcTgChatRepository;

    @Override
    public void register(Long tgChatId) {
        jdbcTgChatRepository.add(tgChatId);
    }

    @Override
    public void removeUser(Long tgChatId) {
        jdbcTgChatRepository.remove(tgChatId);
    }
}
