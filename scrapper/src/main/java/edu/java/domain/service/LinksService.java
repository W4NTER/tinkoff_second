package edu.java.domain.service;

import edu.java.domain.dto.LinksDTO;
import java.net.URI;
import java.util.List;

public interface LinksService {
    LinksDTO addLink(Long tgChatId, URI url);

    LinksDTO deleteLink(Long tgChatId, URI url);

    List<LinksDTO> listAll(Long tgChatId);
}
