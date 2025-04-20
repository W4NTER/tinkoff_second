package edu.java.service.jpa;

import edu.java.domain.repository.jpa.JpaChatRepository;
import edu.java.entity.Chat;
import edu.java.service.TgChatService;
import java.time.OffsetDateTime;
import org.springframework.transaction.annotation.Transactional;

public class JpaChatService implements TgChatService {
    private final JpaChatRepository jpaChatRepository;

    public JpaChatService(JpaChatRepository jpaChatRepository) {
        this.jpaChatRepository = jpaChatRepository;
    }

    @Override
    @Transactional
    public void register(Long tgChatId) {
        jpaChatRepository.save(new Chat(tgChatId, OffsetDateTime.now()));
    }

    @Override
    @Transactional
    public void removeUser(Long tgChatId) {
        jpaChatRepository.deleteById(tgChatId);
    }
}
