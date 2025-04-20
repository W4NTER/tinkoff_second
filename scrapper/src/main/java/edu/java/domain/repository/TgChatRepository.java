package edu.java.domain.repository;

import edu.java.domain.dto.ChatDTO;
import java.util.List;
import java.util.Optional;

public interface TgChatRepository {
    void add(Long chatId);

    void remove(Long id);

    List<ChatDTO> findAll();

    Optional<ChatDTO> findById(Long chatId);
}
