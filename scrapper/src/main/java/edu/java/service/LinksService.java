package edu.java.service;

import edu.java.domain.dto.LinksDTO;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinksService {
    LinksDTO addLink(Long tgChatId, URI url);

    LinksDTO deleteLink(Long tgChatId, URI url);

    List<LinksDTO> listAll(Long tgChatId);

    OffsetDateTime lastUpdate(URI url);

    List<LinksDTO> findAllOutdatedLinks();

    void add(URI link, OffsetDateTime lastUpdate);

    List<Long> findAllChatIdsByLink(URI url);

    List<LinksDTO> findAll();
}
