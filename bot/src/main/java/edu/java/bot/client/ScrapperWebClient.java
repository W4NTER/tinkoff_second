package edu.java.bot.client;

import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class ScrapperWebClient {

    private final WebClient webClient;

    private final static String TG_CHAT_URI = "/tg-chat/";
    private final static String LINKS_URI = "/links";
    private final static String HEADER = "Tg-Chat-Id";

    public String registerChat(Long chatId) {
        return webClient
                .post()
                .uri(TG_CHAT_URI + chatId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String deleteChat(Long chatId) {
        return webClient
                .method(HttpMethod.DELETE)
                .uri(TG_CHAT_URI + chatId)
                .body(Mono.just(chatId), Long.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getAllTrackedLinks(Long chatId) {
        return webClient
                .get()
                .uri(LINKS_URI)
                .header(HEADER, String.valueOf(chatId))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String trackLink(Long chatId, URI link) {
        return webClient
                .post()
                .uri(LINKS_URI)
                .header(HEADER, String.valueOf(chatId))
                .body(Mono.just(link), URI.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String untrackLink(Long chatId, URI link) {
        return webClient
                .method(HttpMethod.DELETE)
                .uri(LINKS_URI)
                .header(HEADER, String.valueOf(chatId))
                .body(Mono.just(link), URI.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
