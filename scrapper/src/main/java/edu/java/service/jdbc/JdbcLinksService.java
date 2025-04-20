package edu.java.service.jdbc;

import edu.java.client.github.GitHubClient;
import edu.java.client.stackoverflow.StackoverflowClient;
import edu.java.domain.dto.LinksDTO;
import edu.java.domain.repository.CommunicationsRepository;
import edu.java.domain.repository.LinksRepository;
import edu.java.service.LinksService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;


public class JdbcLinksService implements LinksService {
    private final LinksRepository linksRepository;
    private final CommunicationsRepository communicationsRepository;
    private final GitHubClient gitHubClient;
    private final StackoverflowClient stackoverflowClient;
    private final static String GIT_REFERENCE = "github.com";
    private final static String STACKOVERFLOW_REFERENCE = "stackoverflow.com";
    private final static Logger LOGGER = LogManager.getLogger();

    public JdbcLinksService(
            LinksRepository linksRepository,
            GitHubClient gitHubClient,
            StackoverflowClient stackoverflowClient,
            CommunicationsRepository communicationsRepository) {
        this.linksRepository = linksRepository;
        this.gitHubClient = gitHubClient;
        this.stackoverflowClient = stackoverflowClient;
        this.communicationsRepository = communicationsRepository;
    }


    @Override
    @Transactional
    public LinksDTO addLink(Long tgChatId, URI url) {
        var links = linksRepository.findAll();
        var linksInBD = links.stream().filter(link -> url.equals(link.link())).toList();
        if (!linksInBD.isEmpty()) {
            return linksInBD.getFirst();
        }

        linksRepository.add(url, lastUpdate(url));
        Long linkId = linksRepository.getLink(url).id();
        communicationsRepository.add(tgChatId, linkId);
        return linksRepository.getLink(url);
    }

    @Override
    @Transactional
    public LinksDTO deleteLink(Long tgChatId, URI url) {
        LinksDTO link = linksRepository.getLink(url);
        linksRepository.remove(url);
        return link;
    }

    @Override
    public List<LinksDTO> listAll(Long tgChatId) {
        return linksRepository.findAll();
    }

    //сделать метод в интерфейсе
    @Transactional
    public OffsetDateTime lastUpdate(URI url) {
        if (url.toString().contains(GIT_REFERENCE)) {
            LOGGER.info(url.toString());
            return gitHubClient.getLastUpdate(url.toString()).updatedAt();
        } else if (url.toString().contains(STACKOVERFLOW_REFERENCE)) {
            return stackoverflowClient.getLastActivity(url.toString()).lastActivityDate();
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    @Transactional
    public List<LinksDTO> findAllOutdatedLinks() {
        return linksRepository.findAllOutdatedLinks();
    }

    @Override
    @Transactional
    public void add(URI link, OffsetDateTime lastUpdate) {
        linksRepository.add(link, lastUpdate);
    }

    @Override
    public List<Long> findAllChatIdsByLink(URI url) {
        Long linkId = linksRepository.getLink(url).id();
        return communicationsRepository.findChatsByLink(linkId);
    }

    @Override
    public List<LinksDTO> findAll() {
        return linksRepository.findAll();
    }
}
