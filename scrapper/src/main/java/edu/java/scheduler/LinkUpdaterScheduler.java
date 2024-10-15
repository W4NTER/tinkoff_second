package edu.java.scheduler;

import edu.java.client.botClient.BotWebClient;
import edu.java.client.github.GitHubClient;
import edu.java.client.stackoverflow.StackoverflowClient;
import edu.java.domain.dto.LinksDTO;
import edu.java.service.LinksService;
import java.time.OffsetDateTime;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Setter
public class LinkUpdaterScheduler {
    private final static Logger LOGGER = LogManager.getLogger();
    private final static String GIT_URL = "github.com";
    private final static String STACKOVERFLOW_URL = "stackoverflow.com";
    private int counter;
    private final LinksService linksService;
    private final GitHubClient gitHubClient;
    private final BotWebClient botWebClient;
    private final StackoverflowClient stackoverflowClient;

    public LinkUpdaterScheduler(LinksService linksService,
                                GitHubClient gitHubClient,
                                BotWebClient botWebClient,
                                StackoverflowClient stackoverflowClient) {
        this.linksService = linksService;
        this.gitHubClient = gitHubClient;
        this.botWebClient = botWebClient;
        this.stackoverflowClient = stackoverflowClient;
    }

    @Scheduled(fixedDelayString = "#{@scheduler.interval()}")
    public void update() {
        var linksNotUpdatedLongTime = linksService.findAllOutdatedLinks();
        LOGGER.info("Links to check: " + linksNotUpdatedLongTime.size());
        for (LinksDTO link : linksNotUpdatedLongTime) {
            var lastUpdateFromClient = getLastUpdate(link);
            if (!link.lastUpdate().isEqual(lastUpdateFromClient)) {
                Long[] chatIds = linksService.findAllChatIdsByLink(link.link()).toArray(Long[]::new);
                botWebClient.sendUpdates(link.id(), link.link().toString(), "Есть изменения ", chatIds);
            }
            linksService.add(link.link(), lastUpdateFromClient);
        }
        setCounter(1 + counter);
        LOGGER.info("update method called " + counter + " time(s)");
    }


    private OffsetDateTime getLastUpdate(LinksDTO links) {
        OffsetDateTime lastUpdate;
        if (linkToArray(links)[2].equals(GIT_URL)) {
            lastUpdate = gitHubClient.getLastUpdate(links.link().toString()).updatedAt();
        } else if (linkToArray(links)[2].equals(STACKOVERFLOW_URL)) {
            lastUpdate = stackoverflowClient.getLastActivity(links.link().toString()).lastActivityDate();
        } else {
            lastUpdate = null;
        }
        return lastUpdate;
    }

    private String[] linkToArray(LinksDTO links) {
        return links.link().toString().split("/");
    }
}
