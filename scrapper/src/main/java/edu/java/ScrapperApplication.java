package edu.java;

import edu.java.client.GitHubClient;
import edu.java.configuration.ApplicationConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;



@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class ScrapperApplication {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        SpringApplication.run(ScrapperApplication.class, args);
        GitHubClient client = new GitHubClient("https://github.com/");
        LOGGER.info(client.getSomeInfo("W4NTER", "tinkoff_second"));
    }
}
