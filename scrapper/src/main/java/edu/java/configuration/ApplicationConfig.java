package edu.java.configuration;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;


@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
        @Bean
        @NotNull
        Scheduler scheduler,
        @NotNull
        String baseUrlGit,
        @NotNull
        String baseUrlStackoverflow,
        @NotNull
        String baseUrlBot,
        @NotEmpty
        String gitToken,
        @NotNull
        AccessType databaseAccessType
) {

    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

    public enum AccessType {
        JDBC, JPA
    }
}
