package edu.java.client.botClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.client.botClient.dto.BotClientDTO;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@AllArgsConstructor
public class BotWebClient {

    private final WebClient botClient;
    private final static Logger LOGGER = LogManager.getLogger();

    public String sendUpdates(Long linkId, String url, String description, Long[] chatIds) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
//        String body = String.format("{\"id\":%d, \"url\":\"%s\", \"description\":\"%s\", \"tgChatId\": [%d]}",
//                linkId, url, description, chatIds);
            String body = objectMapper.writeValueAsString(new BotClientDTO(linkId, url, description, chatIds));

            LOGGER.info(body);
            return botClient
                    .post()
                    .uri("/updates")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
        }
        return null;
    }
}
