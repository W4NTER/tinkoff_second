package edu.java.domain.repository;

import edu.java.domain.dto.ChatDTO;
import java.util.List;

public interface ChatRepository {
    void add();

    void remove(Long id);

    List<ChatDTO> findAll();
}
