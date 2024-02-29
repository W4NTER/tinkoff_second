package edu.java.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.java.dto.RepositoryResponse;
import org.springframework.web.reactive.function.client.WebClient;

public class GitHubClient {
    private final String  baseUrl;
    private final WebClient webClient;
    private static final int MAX_BUFFER = 16 * 1024 * 1024;

    public GitHubClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.webClient = WebClient.builder().codecs(codecs ->
                codecs.defaultCodecs().maxInMemorySize(MAX_BUFFER)).baseUrl(baseUrl).build();
    }

    public String getSomeInfo(String user, String repo) {
        return webClient.get().uri(baseUrl + user + "/" + repo).retrieve().bodyToMono(String.class).block();
    }

    public RepositoryResponse getLastUpdate(String userName, String repo) {
        return parse(webClient.get().uri(baseUrl + userName + "/" + repo)
                .retrieve().bodyToMono(String.class).block());

    }

    private RepositoryResponse parse(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            return objectMapper.readValue(json, RepositoryResponse.class);
        } catch (Exception e) {
            return null;
        }
    }
}
