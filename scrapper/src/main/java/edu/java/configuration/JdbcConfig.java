package edu.java.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;


@Configuration
@EnableTransactionManagement
@ComponentScan
@SuppressWarnings({"MultipleStringLiterals"})
public class JdbcConfig {

    @Bean(name = "entityManagerFactory")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/scrapper");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
//
//    @Bean
//    public PlatformTransactionManager platformTransactionManager() {
//        return new DataSourceTransactionManager(dataSource());
//    }
}

