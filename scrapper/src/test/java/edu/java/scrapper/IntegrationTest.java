package edu.java.scrapper;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.DirectoryResourceAccessor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.sql.DriverManager;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@ActiveProfiles("test")
public abstract class IntegrationTest {
    public static PostgreSQLContainer<?> POSTGRES;
    private final static Logger LOGGER = LogManager.getLogger();

    static {
        POSTGRES = new PostgreSQLContainer<>("postgres:16")
                .withDatabaseName("test_db")
                .withUsername("postgres")
                .withPassword("postgres");
        POSTGRES.start();

        runMigrations(POSTGRES);
    }

    public static void runMigrations(PostgreSQLContainer<?> c)  {
        try (var connection = DriverManager.getConnection(c.getJdbcUrl(), c.getUsername(), c.getPassword())) {
            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(
                            new JdbcConnection(connection));

            var changelogPath =
                    new File(".")
                            .toPath()
                            .toAbsolutePath()
                            .getParent()
                            .getParent()
                            .resolve("migrations");

            Liquibase liquibase =
                    new liquibase
                            .Liquibase("master.xml",
                            new DirectoryResourceAccessor(changelogPath),
                            database);

            liquibase.update(new Contexts(), new LabelExpression());
        } catch (Exception e) {
            throw new RuntimeException("Migration error", e);
        }
    }

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @Test
    void testThatContainerIsCreatedReturnedSucceed() {
        assertTrue(POSTGRES.isCreated());
    }

    @Test
    void testThatContainerIsCreatedReturned() {
        LOGGER.info(POSTGRES.getDatabaseName());
        LOGGER.info(POSTGRES.getJdbcUrl());
        assertEquals(POSTGRES.getDatabaseName(), "test_db");
    }
}