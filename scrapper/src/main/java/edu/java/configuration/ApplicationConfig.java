package edu.java.configuration;

import edu.java.client.GitClient;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
        @NotNull
        Scheduler scheduler
) {
    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

    @Bean
    public GitClient gitClient() {
        return new GitClient("https://github.com/");
    }

//    @Bean
//    public WebClient gitHubWebClient() {
//        return WebClient.builder()
//                .baseUrl("https://github.com")
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .exchangeStrategies(ExchangeStrategies.builder()
//                        .codecs(configurer -> configurer
//                                .defaultCodecs()
//                                .maxInMemorySize(16 * 1024 * 1024)) // Set max buffer size to 16MB
//                        .build())
//                .build();
//    }
}
