package edu.java.scheduler;

import edu.java.client.botClient.BotWebClient;
import edu.java.client.github.GitHubClientImpl;
import edu.java.client.stackoverflow.StackoverflowClientImpl;
import edu.java.domain.dto.CommunicationsDTO;
import edu.java.domain.dto.LinksDTO;
import edu.java.domain.repository.CommunicationsRepository;
import edu.java.domain.repository.LinksRepository;
import java.time.OffsetDateTime;
import java.util.Objects;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Setter
public class LinkUpdaterScheduler {
    private final static Logger LOGGER = LogManager.getLogger();
    private int counter;
    @Autowired
    private LinksRepository linksRepository;
    @Autowired
    private CommunicationsRepository communicationsRepository;
    private final String gitUrl = "github.com";
    private final String stackoverflowUrl = "stackoverflow.com";
    @Autowired
    private GitHubClientImpl gitHubClient;
    @Autowired
    private BotWebClient botWebClient;
    @Autowired
    private StackoverflowClientImpl stackoverflowClient;

    @Scheduled(fixedDelayString = "#{@scheduler.interval()}")
    public void update() {
        var linksNotUpdatedLongTime = linksRepository.findAllOutdatedLinks();
        for (LinksDTO link : linksNotUpdatedLongTime) {
            var lastUpdateFromClient = getLastUpdate(link);
            if (!link.lastUpdate().isEqual(lastUpdateFromClient)) {
                Long[] chatIds = communicationsRepository.findAll().stream()
                        .filter(obj -> Objects.equals(obj.linkId(), link.id()))
                        .map(CommunicationsDTO::chatId).toArray(Long[]::new);
                botWebClient.sendUpdates(link.id(), link.link().toString(), "Есть изменения ", chatIds);
            }
                linksRepository.add(link.link(), lastUpdateFromClient);
        }
        setCounter(1 + counter);
        LOGGER.info("update method called " + counter + " time(s)");
    }


    private OffsetDateTime getLastUpdate(LinksDTO linksDTO) {
        OffsetDateTime lastUpdate;
        if (linkToArray(linksDTO)[2].equals(gitUrl)) {
            lastUpdate = gitHubClient.getLastUpdate(linksDTO.link().toString()).updatedAt();
        } else if (linkToArray(linksDTO)[2].equals(stackoverflowUrl)) {
            lastUpdate = stackoverflowClient.getLastActivity(linksDTO.link().toString()).lastActivityDate();
        } else {
            lastUpdate = null;
        }
        return lastUpdate;
    }

    private String[] linkToArray(LinksDTO linksDTO) {
        return linksDTO.link().toString().split("/");
    }
}
