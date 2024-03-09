package edu.java.bot.client;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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
                .delete()
                .uri(TG_CHAT_URI + id)
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
        return webClient
                .post()
                .uri(LINKS_URI)
                .header(HEADER, String.valueOf(id))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String untrackLink(Long id) {
        return webClient
                .delete()
                .uri(LINKS_URI)
                .header(HEADER, String.valueOf(id))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
