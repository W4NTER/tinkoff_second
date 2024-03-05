package edu.java.configuration;

import edu.java.client.github.GitHubClientImpl;
import edu.java.client.stackoverflow.StackoverflowClientImpl;
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
