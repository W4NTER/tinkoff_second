package edu.java.service.jpa;

import edu.java.client.github.GitHubClient;
import edu.java.client.stackoverflow.StackoverflowClient;
import edu.java.domain.dto.LinksDTO;
import edu.java.domain.repository.jpa.JpaChatRepository;
import edu.java.domain.repository.jpa.JpaLinksRepository;
import edu.java.entity.Chat;
import edu.java.entity.Links;
import edu.java.service.LinksService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;



public class JpaLinksService implements LinksService {
    private static final int TIME_IN_MINUTES_TO_OUTDATED_LINK = 5;
    private final JpaChatRepository jpaChatRepository;
    private final JpaLinksRepository jpaLinksRepository;
    private final static OffsetDateTime TIME_NOW = OffsetDateTime.now();
    private final static String GIT_REFERENCE = "github.com";
    private final static String STACKOVERFLOW_REFERENCE = "stackoverflow.com";
    private final GitHubClient gitHubClient;
    private final StackoverflowClient stackoverflowClient;

    public JpaLinksService(JpaChatRepository jpaChatRepository,
                           JpaLinksRepository jpaLinksRepository,
                           GitHubClient gitHubClient,
                           StackoverflowClient stackoverflowClient) {
        this.jpaChatRepository = jpaChatRepository;
        this.jpaLinksRepository = jpaLinksRepository;
        this.gitHubClient = gitHubClient;
        this.stackoverflowClient = stackoverflowClient;
    }


    //TODO Можно сделать id линки хэшкодом самой линки, тогда не нужно будет проверять есть ли она в бд
    @Override
    @Transactional
    public LinksDTO addLink(Long tgChatId, URI url) {
        Links link = jpaLinksRepository.findByLink(url.toString());
        if (link == null || link.getFollowingChats().isEmpty()) {
            var chat = jpaChatRepository.findById(tgChatId).orElse(null);
            link = jpaLinksRepository.save(new Links(url.toString(), TIME_NOW, TIME_NOW, TIME_NOW));
            if (chat != null) {
                chat.getFollowingLinks().add(link);
            }
        }
        return castLinksToDTO(link);
    }

    @Override
    @Transactional
    public LinksDTO deleteLink(Long tgChatId, URI url) {
        var chat = jpaChatRepository.findById(tgChatId);
        var link = jpaLinksRepository.findByLink(url.toString());
        chat.ifPresent(value -> {
            value.getFollowingLinks().remove(link);
            if (link != null) {
                link.getFollowingChats().remove(value);
            }
        });

        return link != null ? castLinksToDTO(link) : null;
    }

    @Override
    @Transactional
    public List<LinksDTO> listAll(Long tgChatId) {
        return (jpaLinksRepository.findAll()
                .stream().filter(link ->
                        link.getFollowingChats().stream()
                                .anyMatch(chat -> Objects.equals(chat.getId(), tgChatId)))
                .map(val -> new LinksDTO(val.getId(), URI.create(val.getLink()),
                        val.getCreatedAt(), val.getLastUpdate(), val.getLastCheck()))
                .collect(Collectors.toList())
                );
    }

    @Override
    public OffsetDateTime lastUpdate(URI url) {
        if (url.toString().contains(GIT_REFERENCE)) {
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
        return jpaLinksRepository.findAllOutdatedLinks(
                OffsetDateTime.now().minusMinutes(TIME_IN_MINUTES_TO_OUTDATED_LINK))
                .stream().map(this::castLinksToDTO).toList();
    }

    @Override
    @Transactional
    public void add(URI link, OffsetDateTime lastUpdate) {
        var linkObj = jpaLinksRepository.findByLink(link.toString());
        if (linkObj != null) {
            linkObj.setLastCheck(OffsetDateTime.now());
            linkObj.setLastUpdate(lastUpdate);
            jpaLinksRepository.save(linkObj);
        } else {
            jpaLinksRepository.save(new Links(link.toString(), TIME_NOW, lastUpdate, TIME_NOW));
        }
    }

    @Override
    @Transactional
    public List<Long> findAllChatIdsByLink(URI url) {
        var link = jpaLinksRepository.findByLink(url.toString());
        return link.getFollowingChats().stream().map(Chat::getId).toList();
    }

    @Override
    @Transactional
    public List<LinksDTO> findAll() {
        return jpaLinksRepository.findAll().stream().map(this::castLinksToDTO).toList();
    }

    private LinksDTO castLinksToDTO(Links links) {
        return new LinksDTO(links.getId(), URI.create(links.getLink()),
                links.getCreatedAt(), links.getLastUpdate(), links.getLastCheck());
    }
}
