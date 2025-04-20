package edu.java.configuration;

import edu.java.client.github.GitHubClient;
import edu.java.client.stackoverflow.StackoverflowClient;
import edu.java.domain.repository.CommunicationsRepository;
import edu.java.domain.repository.LinksRepository;
import edu.java.domain.repository.TgChatRepository;
import edu.java.domain.repository.jdbc.JdbcCommunicationsRepository;
import edu.java.domain.repository.jdbc.JdbcLinksRepository;
import edu.java.domain.repository.jdbc.JdbcTgChatRepository;
import edu.java.service.jdbc.JdbcLinksService;
import edu.java.service.jdbc.JdbcTgChatService;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
@Configuration
@EnableTransactionManagement
@ComponentScan
@SuppressWarnings({"MultipleStringLiterals"})
@ComponentScan(basePackages = "edu.java")
public class JdbcConfig {
    private final DataSource dataSource;

    public JdbcConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public CommunicationsRepository communicationsRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcCommunicationsRepository(jdbcTemplate);
    }

    @Bean
    public LinksRepository linksRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcLinksRepository(jdbcTemplate);
    }

    @Bean
    public TgChatRepository tgChatRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcTgChatRepository(jdbcTemplate);
    }

    @Bean
    public JdbcLinksService jdbcLinksService(
            LinksRepository jdbcLinksRepository,
            @Autowired GitHubClient gitHubClient,
            @Autowired StackoverflowClient stackoverflowClient,
            CommunicationsRepository communicationsRepository
            ) {
        return new JdbcLinksService(jdbcLinksRepository, gitHubClient, stackoverflowClient, communicationsRepository);
    }

    @Bean
    public JdbcTgChatService jdbcTgChatService(TgChatRepository tgChatRepository) {
        return new JdbcTgChatService(tgChatRepository);
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}

