package edu.java.domain.repository;

import edu.java.domain.dto.ChatDTO;
import java.util.List;

public interface TgChatRepository {
    void add(Long chatId);

    void remove(Long id);

    List<ChatDTO> findAll();
}
