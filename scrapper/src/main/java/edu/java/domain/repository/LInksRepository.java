package edu.java.domain.repository;

import edu.java.controller.dto.request.AddLinkRequest;
import edu.java.domain.dto.LinksDTO;
import java.util.List;

public interface LInksRepository {
    void add(AddLinkRequest link, Long chatId);

    void remove(Long id);

    List<LinksDTO> findAll();
}
