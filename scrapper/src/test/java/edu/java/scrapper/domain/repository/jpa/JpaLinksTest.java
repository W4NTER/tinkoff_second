package edu.java.scrapper.domain.repository.jpa;

import edu.java.scrapper.IntegrationTest;
import edu.java.service.LinksService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.OffsetDateTime;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class JpaLinksTest extends IntegrationTest {
    private final static URI URL = URI.create("https://github.com/");
    private final static OffsetDateTime TIME_NOW = OffsetDateTime.now();

    @DynamicPropertySource
    static void setJpaProperty(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jpa");
    }

    @Autowired
    private LinksService linksService;

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
