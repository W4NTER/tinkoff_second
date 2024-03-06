package edu.java.scrapper.configuration;

import edu.java.scrapper.client.github.GitHubClientImpl;
import edu.java.scrapper.client.stackoverflow.StackoverflowClientImpl;
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
    public GitHubClientImpl gitHubClient() {
        return new GitHubClientImpl(baseUrlGit());
    }

    @Bean
    public StackoverflowClientImpl stackOverFlowClient() {
        return new StackoverflowClientImpl(baseUrlStackoverflow);
    }
}
