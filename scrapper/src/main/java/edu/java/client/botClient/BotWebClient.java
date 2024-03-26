package edu.java.client.botClient;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@AllArgsConstructor
public class BotWebClient {

    private final WebClient botClient;

    public String sendUpdates(Long linkId, String url, String description, Long chatId) {
        String body = String.format("{\"id\":%d, \"url\":\"%s\", \"description\":\"%s\", \"tgChatId\":%d}",
                linkId, url, description, chatId);
        return botClient
                .post()
                .uri("/updates")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
