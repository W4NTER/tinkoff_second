package edu.java.configuration;


import edu.java.client.GitHubClient;
import edu.java.client.StackOverFlowClient;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
        @NotNull
        Scheduler scheduler,

        @NotNull
        String baseUrlGit,

        @NotNull
        String baseUrlStackOverFlow
) {
    @Bean
    public GitHubClient gitHubClient(ApplicationConfig config) {
        return new GitHubClient(config.baseUrlGit);
    }

    @Bean
    public GitHubClient gitClient(ApplicationConfig config) {
        return new GitHubClient(config.baseUrlGit);
    }

    @Bean
    public StackOverFlowClient stackOverFlowClient(ApplicationConfig config) {
        return new StackOverFlowClient(config.baseUrlStackOverFlow);
    }

    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }
}
