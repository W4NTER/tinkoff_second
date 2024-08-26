package edu.java.client.github;

import edu.java.client.github.dto.GitResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class GitHubClientImpl implements GitHubClient {

    private final WebClient gitHubClient;

    private Mono<GitResponseDTO> getLastUpdateMono(String username, String repo) {
        return gitHubClient.get().uri("/repos/" + username + "/" + repo)
                .retrieve().bodyToMono(GitResponseDTO.class);
    }

    @Override
    public GitResponseDTO getLastUpdate(String username, String repo) {
        return getLastUpdateMono(username, repo).block();
    }
}
