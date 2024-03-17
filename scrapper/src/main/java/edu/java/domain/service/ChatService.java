package edu.java.domain.service;

import edu.java.domain.dto.ChatDTO;

public interface ChatService {
    void add(Long id);

    void remove(Long id);

    ChatDTO findAll();
}
