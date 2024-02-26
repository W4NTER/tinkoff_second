package edu.java.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.java.dto.RepositoryResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

public class GitClient {
    private final Logger LOGGER = LogManager.getLogger();

    @Value("https://github.com/")
    private String baseUrl;
    private final WebClient webClient;

    public GitClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.webClient = WebClient.builder().codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(16*1024*1024)).baseUrl(baseUrl).build();
    }

    public String getSomeInfo(String user, String repo) {
        String request = baseUrl + user + "/" + repo;
        String json = webClient.get().uri(request).retrieve().bodyToMono(String.class).block();
        return json;
    }

    public RepositoryResponse getLastUpdate(String userName, String repo) {
        String request = baseUrl + userName + "/" + repo;
        String json = webClient.get().uri(request).retrieve().bodyToMono(String.class).block();

        return parse(json);
    }

    private RepositoryResponse parse(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            return objectMapper.readValue(json, RepositoryResponse.class);
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return null;
        }
    }
}
