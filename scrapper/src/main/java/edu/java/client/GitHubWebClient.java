//package edu.java.client;
//
//
//import edu.java.dto.RepositoryResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//@Component
//public class GitHubWebClient implements GitHubClient {
//
//    private final WebClient webClient;
//
//    public GitHubWebClient(WebClient webClient) {
//        this.webClient = webClient;
//    }
//
//    public Mono<RepositoryResponse> fetchRepository(String owner, String repo) {
//        return webClient.get()
//                .uri("/repos/{owner}/{repo}", owner, repo)
//                .retrieve()
//                .bodyToMono(RepositoryResponse.class);
//    }
//}
