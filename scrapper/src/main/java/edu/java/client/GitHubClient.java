package edu.java.client;

import edu.java.client.dto.GitResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
public class GitHubClient {
    private final WebClient webClient;

    public GitHubClient(@Qualifier("baseUrlGit") String baseUtlGit) {
        this.webClient = WebClient.builder().baseUrl(baseUtlGit).build();
    }

    public Mono<GitResponseDTO> getLastUpdateMono(String username, String repo) {
        return webClient.get().uri("/repos/" + username + "/" + repo)
                .retrieve().bodyToMono(GitResponseDTO.class);
    }

    public GitResponseDTO getLastUpdate(String username, String repo) {
        return getLastUpdateMono(username, repo).block();
    }
}
