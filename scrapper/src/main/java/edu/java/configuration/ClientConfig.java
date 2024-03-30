package edu.java.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableScheduling
public class ClientConfig {
    @Autowired
    ApplicationConfig applicationConfig;

    @Bean
    public WebClient gitHubClient() {
        return WebClient.builder()
                .baseUrl(applicationConfig.baseUrlGit())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + System.getenv("GIT_TOKEN"))
                .build();
    }

    @Bean
    public WebClient stackOverFlowClient() {
        return WebClient.builder().baseUrl(applicationConfig.baseUrlStackoverflow()).build();
    }

    @Bean
    public WebClient botClient() {
        return WebClient.builder().baseUrl(applicationConfig.baseUrlBot()).build();
    }
}
