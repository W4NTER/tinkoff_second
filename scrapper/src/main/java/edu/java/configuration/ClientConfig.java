package edu.java.configuration;

import edu.java.client.GitHubClient;
import edu.java.client.StackOverFlowClient;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "client", ignoreUnknownFields = false)
@Setter
public class ClientConfig {
    @NotNull
    String baseUrlGit;

    @NotNull
    String baseUrlStackoverflow;

    @Bean
    String baseUrlGit() {
        return baseUrlGit;
    }

    @Bean
    String baseUrlStackoverflow() {
        return baseUrlStackoverflow;
    }

    @Bean
    public GitHubClient gitHubClient() {
        return new GitHubClient(baseUrlGit());
    }

    @Bean
    public StackOverFlowClient stackOverFlowClient() {
        return new StackOverFlowClient(baseUrlStackoverflow);
    }
}
