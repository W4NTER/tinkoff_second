package edu.java.client.github;

import edu.java.client.github.dto.GitResponseDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class GitHubClientImpl implements GitHubClient {

    private final WebClient gitHubClient;
    private final static int DEFAULT_LENGTH = 5;
    private final static int NAME_POSITION_PARTS = 3;
    private final static int REPO_POSITION_PARTS = 4;
    private final static Logger LOGGER = LogManager.getLogger();

    private Mono<GitResponseDTO> getLastUpdateMono(String username, String repo) {
        return gitHubClient.get().uri("/repos/" + username + "/" + repo)
                .retrieve().bodyToMono(GitResponseDTO.class);
    }

    @Override
    public GitResponseDTO getLastUpdate(String url) {
        String[] parts = parseUrl(url);
        if (parts.length != DEFAULT_LENGTH
                && parts.length != DEFAULT_LENGTH + 1) {
            LOGGER.info("Неправильно введена ссылка");
            return null;
        }
        return getLastUpdateMono(
                parts[NAME_POSITION_PARTS],
                parts[REPO_POSITION_PARTS]
        ).block();
    }

    private String[] parseUrl(String url) {
        return url.split("/");
    }
}
