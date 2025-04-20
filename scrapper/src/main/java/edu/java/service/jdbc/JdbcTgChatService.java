package edu.java.service.jdbc;

import edu.java.domain.repository.TgChatRepository;
import edu.java.service.TgChatService;
import jakarta.persistence.EntityNotFoundException;


public class JdbcTgChatService implements TgChatService {
    private final TgChatRepository tgChatRepository;

    public JdbcTgChatService(TgChatRepository tgChatRepository) {
        this.tgChatRepository = tgChatRepository;
    }

    @Override
    public void register(Long tgChatId) {
        if (tgChatRepository.findById(tgChatId).isEmpty()) {
            throw new EntityNotFoundException();
        }
        tgChatRepository.add(tgChatId);
    }

    @Override
    public void removeUser(Long tgChatId) {
        tgChatRepository.remove(tgChatId);
    }
}
