package edu.java.scrapper.domain.repository.jdbc;

import edu.java.repository.LinksRepository;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.OffsetDateTime;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class JdbcLinksRepositoryTest extends IntegrationTest {
    private final static URI URL = URI.create("https://github.com/");
    private final static OffsetDateTime TIME_NOW = OffsetDateTime.now();

    @DynamicPropertySource
    static void setJdbc(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("database-access-type", () -> "jdbc");
    }

    @Autowired
    private LinksRepository linksRepository;

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
