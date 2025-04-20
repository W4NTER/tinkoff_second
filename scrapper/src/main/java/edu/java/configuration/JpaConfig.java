package edu.java.configuration;

import edu.java.client.botClient.BotWebClient;
import edu.java.client.github.GitHubClient;
import edu.java.client.stackoverflow.StackoverflowClient;
import edu.java.domain.repository.jpa.JpaChatRepository;
import edu.java.domain.repository.jpa.JpaLinksRepository;
import edu.java.scheduler.LinkUpdaterScheduler;
import edu.java.service.jpa.JpaChatService;
import edu.java.service.jpa.JpaLinksService;
import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
@Configuration
@EnableTransactionManagement
@ComponentScan
@SuppressWarnings({"MultipleStringLiterals"})
public class JpaConfig {
    private final DataSource dataSource;

    public JpaConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("edu.java.entity");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public JpaLinksService jpaLinksService(
            JpaChatRepository jpaChatRepository,
            JpaLinksRepository jpaLinksRepository,
            @Autowired GitHubClient gitHubClient,
            @Autowired StackoverflowClient stackoverflowClient
            ) {
        return new JpaLinksService(jpaChatRepository, jpaLinksRepository, gitHubClient, stackoverflowClient);
    }

    @Bean
    public LinkUpdaterScheduler linkUpdaterScheduler(
            JpaLinksService jpaLinksService,
            @Autowired GitHubClient gitHubClient,
            @Autowired BotWebClient botWebClient,
            @Autowired StackoverflowClient stackoverflowClient
    ) {
        return new LinkUpdaterScheduler(jpaLinksService,
                gitHubClient, botWebClient, stackoverflowClient);
    }

    @Bean
    JpaChatService jpaChatService(JpaChatRepository jpaChatRepository) {
        return new JpaChatService(jpaChatRepository);
    }
}
