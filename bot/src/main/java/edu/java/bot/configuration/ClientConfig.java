package edu.java.bot.configuration;


import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ConfigurationProperties(prefix = "client")
@Setter
public class ClientConfig {
    String scrapperBaseUrl;

    @Bean
    public WebClient scrapperClient() {
        return WebClient.builder().baseUrl(scrapperBaseUrl).build();
    }
}

