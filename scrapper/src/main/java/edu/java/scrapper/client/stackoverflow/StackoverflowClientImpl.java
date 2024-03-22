package edu.java.scrapper.client.stackoverflow;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.java.scrapper.client.stackoverflow.dto.StackoverflowResponseDTO;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
@RequiredArgsConstructor
public class StackoverflowClientImpl implements StackoverflowClient {
    private final static Logger LOGGER = LogManager.getLogger();
    private static final int DEFAULT_LENGTH = 6;
    private final WebClient stackOverFlowClient;
    private final String SEARCH_QUERY = "order=desc&sort=activity&site=stackoverflow&filter=withbody";


    public StackoverflowResponseDTO getLastActivity(String userLink) {
        return parse(stackOverFlowClient.get().uri(getResponseUrl(getId(userLink)))
                .retrieve().bodyToMono(String.class).block());
    }

    private String getId(String link) {
        String[] parts = link.split("/");
        if (parts.length == DEFAULT_LENGTH) {
            return parts[parts.length - 2];
        } else {
            return null;
        }
    }

    private String getResponseUrl(String id) {
        return "/" + id + "?" + SEARCH_QUERY;
    }

    private StackoverflowResponseDTO parse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            var node = objectMapper.readTree(response);
            if (!node.isEmpty() && node.has("items") && node.get("items").isArray() && node.get("items").size() > 0) {
                node = node.get("items").get(0);

            var instant = Instant.ofEpochSecond(node.get("last_activity_date").asLong());
            OffsetDateTime lastActivityDate = instant.atZone(ZoneOffset.systemDefault()).toOffsetDateTime();

            return new StackoverflowResponseDTO(
                    node.get("question_id").asLong(),
                    node.get("title").asText(),
                    lastActivityDate);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
