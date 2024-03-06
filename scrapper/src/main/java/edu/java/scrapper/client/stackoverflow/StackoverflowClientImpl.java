package edu.java.scrapper.client.stackoverflow;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.java.scrapper.client.stackoverflow.dto.StackoverflowResponseDTO;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
public class StackoverflowClientImpl implements StackoverflowClient {
    private final WebClient webClient;

    public StackoverflowClientImpl(@Qualifier("baseUrlStackoverflow") String baseUrlStackOverFlow) {
        this.webClient = WebClient.builder().baseUrl(baseUrlStackOverFlow).build();
    }


    public StackoverflowResponseDTO getLastActivity(String userLink) {
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

    private StackoverflowResponseDTO parse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            JsonNode node = objectMapper.readTree(response).get("items").get(0);
            var instant = Instant.ofEpochSecond(node.get("last_activity_date").asLong());
            OffsetDateTime lastActivityDate = OffsetDateTime.ofInstant(instant, ZoneId.of("UTC"));

            return new StackoverflowResponseDTO(
                    node.get("question_id").asLong(),
                    node.get("title").asText(),
                    lastActivityDate);
        } catch (Exception e) {
            return null;
        }
    }
}
