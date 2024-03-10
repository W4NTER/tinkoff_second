package edu.java.bot.client;

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
    private final static String LINKS_URI = "/links/";
    private final static String HEADER = "Tg-Chat-Id";

    public String registerChat(Long id) {
        return webClient
                .post()
                .uri(TG_CHAT_URI + id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String deleteChat(Long id) {
        return webClient
                .method(HttpMethod.DELETE)
                .uri(TG_CHAT_URI + id)
                .body(Mono.just(id), Long.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getAllTrackedLinks(Long id) {
        return webClient
                .get()
                .uri(LINKS_URI)
                .header(HEADER, String.valueOf(id))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String trackLink(Long id) { //Должно быть больше входных данных
        return webClient // Не разобрался как передавать данные, которые фильтруются dto другого приложения, возможно просто String?
                .post()
                .uri(LINKS_URI)
                .header(HEADER, String.valueOf(id))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String untrackLink(Long id) {
        return webClient
                .method(HttpMethod.DELETE)
                .uri(LINKS_URI)
                .header(HEADER, String.valueOf(id))
                .body(Mono.just(id), Long.class)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
