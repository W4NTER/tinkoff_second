package edu.java.configuration;

import edu.java.client.GitHubClient;
import edu.java.client.StackOverFlowClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    @Bean
    public GitHubClient gitHubClient(@Value("${client.base-url-git}") String baseUrlGit) {
        return new GitHubClient(baseUrlGit);
    }

    @Bean
    public StackOverFlowClient stackOverFlowClient(
            @Value("${client.base-url-stackoverflow}") String baseUrlStackoverflow) {
        return new StackOverFlowClient(baseUrlStackoverflow);
    }
}
