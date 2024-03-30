package edu.java.domain.repository;

import edu.java.domain.dto.CommunicationsDTO;
import java.util.List;

public interface CommunicationsRepository {
    void add(Long chatId, Long linkId);

    void remove(Long chatId, Long linkId);

    List<CommunicationsDTO> findAll();
}
