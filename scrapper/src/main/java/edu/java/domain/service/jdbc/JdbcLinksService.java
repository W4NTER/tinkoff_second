package edu.java.domain.service.jdbc;

import edu.java.domain.dto.LinksDTO;
import edu.java.domain.repository.jdbc.JdbcLinksRepository;
import edu.java.domain.service.LinksService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinksService implements LinksService {
    @Autowired
    private JdbcLinksRepository jdbcLinksRepository;

    @Override
    public LinksDTO addLink(Long tgChatId, URI url) {
        var links = jdbcLinksRepository.findAll(tgChatId);
        var linksInBD = links.stream().filter(link -> url.equals(link.link())).toList();
        if (!linksInBD.isEmpty()) {
            return linksInBD.getFirst();
        }
        jdbcLinksRepository.add(url, tgChatId);
        return jdbcLinksRepository.getLink(url, tgChatId);
    }

    @Override
    public LinksDTO deleteLink(Long tgChatId, URI url) {
        LinksDTO link = jdbcLinksRepository.getLink(url, tgChatId);
        jdbcLinksRepository.remove(url, tgChatId);
        return link;
    }

    @Override
    public List<LinksDTO> listAll(Long tgChatId) {
        return jdbcLinksRepository.findAll(tgChatId);
    }
}
