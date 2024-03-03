package edu.java.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.java.dto.GitResponseDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
public class GitHubClient {

    private String baseUrlGit;
    private final WebClient webClient;
    private static final int MAX_BUFFER = 16 * 1024 * 1024;
    private final static Logger LOGGER = LogManager.getLogger();

    public GitHubClient(String baseUrlGit) {
        this.baseUrlGit = baseUrlGit;
        this.webClient = WebClient.builder().codecs(codecs ->
                codecs.defaultCodecs().maxInMemorySize(MAX_BUFFER)).baseUrl(baseUrlGit).build();
    }

    public GitResponseDTO getLastUpdate(String userName, String repo) {
        return parse(webClient.get().uri(baseUrlGit + "repos/" + userName + "/" + repo)
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
