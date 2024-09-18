package edu.java.domain.service.jdbc;

import edu.java.domain.repository.TgChatRepository;
import edu.java.domain.service.TgChatService;


public class JdbcTgChatService implements TgChatService {
    private final TgChatRepository tgChatRepository;

    public JdbcTgChatService(TgChatRepository tgChatRepository) {
        this.tgChatRepository = tgChatRepository;
    }

    @Override
    public void register(Long tgChatId) {
        tgChatRepository.add(tgChatId);
    }

    @Override
    public void removeUser(Long tgChatId) {
        tgChatRepository.remove(tgChatId);
    }
}
