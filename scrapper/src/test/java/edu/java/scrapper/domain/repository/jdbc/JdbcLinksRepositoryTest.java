package edu.java.scrapper.domain.repository.jdbc;

import edu.java.domain.repository.LinksRepository;
import edu.java.scrapper.IntegrationTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.net.URI;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class JdbcLinksRepositoryTest extends IntegrationTest {
    private final static URI URL = URI.create("https://github.com/");
    private final static OffsetDateTime TIME_NOW = OffsetDateTime.now();
    private final static Logger LOGGER = LogManager.getLogger();

    @DynamicPropertySource
    static void setJdbc(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", () -> "jdbc:tc:postgresql:16:///test_db");
//        registry.add("spring.datasource.driver-class-name",
//                () -> "org.testcontainers.jdbc.ContainerDatabaseDriver");
//        registry.add("spring.datasource.username", () -> "");
//        registry.add("spring.datasource.password", () -> "");
        registry.add("app.database-access-type", () -> "jdbc");
    }

    @Autowired
    private LinksRepository linksRepository;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() throws SQLException {
        LOGGER.error("database url = {}", dataSource.getConnection().getMetaData().getURL());
    }

    @Test
    @Transactional
    @Rollback
    void addTest() {
        int linksSize = linksRepository.findAll().size();
        linksRepository.add(URL, TIME_NOW);
        var obj = linksRepository.findAll();
        assertEquals(obj.size(), linksSize + 1);
    }

    @Test
    @Transactional
    @Rollback
    void deleteTest() {
        int linksSize = linksRepository.findAll().size();
        linksRepository.add(URL, TIME_NOW);

        var links = linksRepository.findAll();
        assertEquals(links.size(), linksSize + 1); // проверяем что запись была добавлена


        linksRepository.remove(URL);// проверяем что запись была удалена
        var afterDel = linksRepository.findAll();
        assertEquals(afterDel.size(), linksSize);
    }

    @Test
    @Transactional
    @Rollback
    void findAll() {
        int linksSize = linksRepository.findAll().size();
        linksRepository.add(URL, TIME_NOW);
        var allVal = linksRepository.findAll();
        assertEquals(allVal.size(), linksSize + 1);
    }
}
