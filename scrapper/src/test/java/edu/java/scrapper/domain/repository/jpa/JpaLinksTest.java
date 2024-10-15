package edu.java.scrapper.domain.repository.jpa;

import edu.java.service.LinksService;
import edu.java.service.TgChatService;
import org.apache.kafka.test.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.OffsetDateTime;

import static edu.java.scrapper.IntegrationTest.POSTGRES;
import static org.junit.Assert.assertEquals;

@SpringBootTest
public class JpaLinksTest implements IntegrationTest {
    private final static URI URL = URI.create("https://github.com/");
    private final static OffsetDateTime TIME_NOW = OffsetDateTime.now();

    @DynamicPropertySource
    static void setJpaProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("database-access-type", () -> "jpa");
    }

    @Autowired
    private LinksService linksService;
    @Autowired
    private TgChatService tgChatService;

    @Test
    @Transactional
    @Rollback
    void addLinkTest() {
        int linksSize = linksService.findAll().size();
        linksService.add(URL, TIME_NOW);
        var obj = linksService.findAll();
        assertEquals(obj.size(), linksSize + 1);
    }

    @Test
    @Transactional
    @Rollback
    void findAll() {
        int linksSize = linksService.findAll().size();
        linksService.add(URL, TIME_NOW);
        var allVal = linksService.findAll();
        assertEquals(allVal.size(), linksSize + 1);
    }
}
