package edu.java.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableScheduling
public class ClientConfig {
    private final static Logger LOGGER = LogManager.getLogger();
    final ApplicationConfig applicationConfig;

    public ClientConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

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
