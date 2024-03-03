package edu.java.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.java.client.dto.StackOverFlowResponseDTO;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
public class StackOverFlowClient {
    private final WebClient webClient;
    private static final int MAX_BUFFER = 16 * 1024 * 1024;

    public StackOverFlowClient(String baseUrlStackOverFlow) {
        this.webClient = WebClient.builder().codecs(codecs ->
                codecs.defaultCodecs().maxInMemorySize(MAX_BUFFER)).baseUrl(baseUrlStackOverFlow).build();
    }


    public StackOverFlowResponseDTO getLastActivity(String userLink) {
        return parse(webClient.get().uri(getResponseUrl(getId(userLink)))
                .retrieve().bodyToMono(String.class).block());
    }

    private String getId(String link) {
        String[] parts = link.split("/");
        return parts[parts.length - 2];
    }

    private String getResponseUrl(String id) {
        String searchQuery = "order=desc&sort=activity&site=stackoverflow&filter=withbody";
        return "/" + id + "?" + searchQuery;

    }

    private StackOverFlowResponseDTO parse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            JsonNode node = objectMapper.readTree(response).get("items").get(0);
            var instant = Instant.ofEpochSecond(node.get("last_activity_date").asLong());
            OffsetDateTime date = OffsetDateTime.ofInstant(instant, ZoneId.of("UTC"));

            return new StackOverFlowResponseDTO(node.get("question_id").asLong(), node.get("title").asText(), date);
        } catch (Exception e) {
            return null;
        }
    }
}
