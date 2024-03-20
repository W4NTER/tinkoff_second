package edu.java.domain.repository;

import edu.java.domain.dto.LinksDTO;
import java.net.URI;
import java.util.List;

public interface LInksRepository {
    void add(URI link, Long chatId);

    void remove(URI link, Long chatId);

    List<LinksDTO> findAll(Long tgChatId);
}
