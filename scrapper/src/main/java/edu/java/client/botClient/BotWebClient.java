package edu.java.client.botClient;

import edu.java.controller.dto.response.ListLinksResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@AllArgsConstructor
public class BotWebClient {

    private final WebClient webClient;

    public String sendUpdates(ListLinksResponse listLinksResponse) {
        return webClient
                .post()
                .uri("/updates")
                .body(BodyInserters.fromValue(listLinksResponse))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
