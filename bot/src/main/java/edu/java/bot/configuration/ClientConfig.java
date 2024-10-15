package edu.java.bot.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {
    @Autowired ApplicationConfig config;

    @Bean
    public WebClient scrapperClient() {
        return WebClient.builder().baseUrl(config.baseUrlScrapper()).build();
    }
}

