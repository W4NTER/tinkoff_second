package edu.java.domain.service.jdbc;

import edu.java.client.github.GitHubClientImpl;
import edu.java.client.stackoverflow.StackoverflowClientImpl;
import edu.java.configuration.ApplicationConfig;
import edu.java.domain.dto.LinksDTO;
import edu.java.domain.repository.jdbc.JdbcCommunicationsRepository;
import edu.java.domain.repository.jdbc.JdbcLinksRepository;
import edu.java.domain.service.LinksService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinksService implements LinksService {
    private final JdbcLinksRepository jdbcLinksRepository;
    private final JdbcCommunicationsRepository communicationsRepository;
    private final GitHubClientImpl gitHubClient;
    private final StackoverflowClientImpl stackoverflowClient;
    private final String gitReference = "github.com";
    private final String stackoverflowReference = "stackoverflow.com";

    public JdbcLinksService(
            ApplicationConfig applicationConfig,
            JdbcLinksRepository jdbcLinksRepository,
            GitHubClientImpl gitHubClient,
            StackoverflowClientImpl stackoverflowClient,
            JdbcCommunicationsRepository communicationsRepository) {
        this.jdbcLinksRepository = jdbcLinksRepository;
        this.gitHubClient = gitHubClient;
        this.stackoverflowClient = stackoverflowClient;
        this.communicationsRepository = communicationsRepository;
    }


    @Override
    public LinksDTO addLink(Long tgChatId, URI url) {
        var links = jdbcLinksRepository.findAll();
        var linksInBD = links.stream().filter(link -> url.equals(link.link())).toList();
        if (!linksInBD.isEmpty()) {
            return linksInBD.getFirst();
        }

        jdbcLinksRepository.add(url, lastUpdate(url));
        Long linkId = jdbcLinksRepository.getLink(url).id();
        communicationsRepository.add(tgChatId, linkId);
        return jdbcLinksRepository.getLink(url);
    }

    @Override
    public LinksDTO deleteLink(Long tgChatId, URI url) {
        LinksDTO link = jdbcLinksRepository.getLink(url);
        jdbcLinksRepository.remove(url);
        return link;
    }

    @Override
    public List<LinksDTO> listAll(Long tgChatId) {
        return jdbcLinksRepository.findAll();
    }

    public OffsetDateTime lastUpdate(URI url) {
        if (url.toString().contains(gitReference)) {
            return gitHubClient.getLastUpdate(url.toString()).updatedAt();
        } else if (url.toString().contains(stackoverflowReference)) {
            return stackoverflowClient.getLastActivity(url.toString()).lastActivityDate();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
