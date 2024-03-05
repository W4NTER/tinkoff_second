package edu.java.client.github;

import edu.java.client.github.dto.GitResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
public class GitHubClientImpl implements GitHubClient {
    private final WebClient webClient;

    public GitHubClientImpl(@Qualifier("baseUrlGit") String baseUtlGit) {
        this.webClient = WebClient.builder().baseUrl(baseUtlGit).build();
    }

    private Mono<GitResponseDTO> getLastUpdateMono(String username, String repo) {
        return webClient.get().uri("/repos/" + username + "/" + repo)
                .retrieve().bodyToMono(GitResponseDTO.class);
    }

    @Override
    public GitResponseDTO getLastUpdate(String username, String repo) {
        return getLastUpdateMono(username, repo).block();
    }
}
