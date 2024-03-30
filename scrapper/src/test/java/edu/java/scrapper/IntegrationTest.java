package edu.java.scrapper;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.DirectoryResourceAccessor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.sql.DriverManager;

import static org.junit.Assert.assertTrue;

@Testcontainers
public abstract class IntegrationTest {
    public static PostgreSQLContainer<?> POSTGRES;
    private static final JdbcDatabaseContainer<?> c;

    static {
        POSTGRES = new PostgreSQLContainer<>("postgres:15")
                .withDatabaseName("scrapper")
                .withUsername("postgres")
                .withPassword("postgres");
        POSTGRES.start();

        c = POSTGRES;
        runMigrations();
    }

    @BeforeAll
    public static void runMigrations()  {
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
    @DirtiesContext
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @Test
    void testThatContainerIsCreatedReturnedSucceed() {
        assertTrue(POSTGRES.isCreated());
    }
}