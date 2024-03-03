package edu.java.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.java.client.dto.GitResponseDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
public class GitHubClient {
    private final WebClient webClient;
    private final static Logger LOGGER = LogManager.getLogger();

    public GitHubClient(String baseUrlGit) {
        this.webClient = WebClient.builder().baseUrl(baseUrlGit).build();
    }

    public GitResponseDTO getLastUpdate(String userName, String repo) {
        return parse(webClient.get().uri("/repos/{userName}/{repo}", userName, repo)
                .retrieve().bodyToMono(String.class).block());
    }

    private GitResponseDTO parse(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            return objectMapper.readValue(json, GitResponseDTO.class);
        } catch (Exception e) {
            return null;
        }
    }
}
