package edu.java.scrapper.domain.repository;

import edu.java.domain.repository.jdbc.JdbcLinksRepository;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.time.OffsetDateTime;
import static org.junit.Assert.assertEquals;

//@SpringBootTest
//public class JdbcLinksRepositoryTest extends IntegrationTest {
//    @Autowired
//    private JdbcLinksRepository jdbcLinksRepository;
//    private final static URI URL = URI.create("https://github.com/");
//    private final static OffsetDateTime TIME_NOW = OffsetDateTime.now();
//
//    @Test
//    @Transactional
//    @Rollback
//    void addTest() {
//        int linksSize = jdbcLinksRepository.findAll().size();
//        jdbcLinksRepository.add(URL, TIME_NOW);
//        var obj = jdbcLinksRepository.findAll();
//        assertEquals(obj.size(), linksSize + 1);
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    void deleteTest() {
//        int linksSize = jdbcLinksRepository.findAll().size();
//        jdbcLinksRepository.add(URL, TIME_NOW);
//
//        var links = jdbcLinksRepository.findAll();
//        assertEquals(links.size(), linksSize + 1); // проверяем что запись была добавлена
//
//
//        jdbcLinksRepository.remove(URL);// проверяем что запись была удалена
//        var afterDel = jdbcLinksRepository.findAll();
//        assertEquals(afterDel.size(), linksSize);
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    void findAll() {
//        int linksSize = jdbcLinksRepository.findAll().size();
//        jdbcLinksRepository.add(URL, TIME_NOW);
//        var allVal = jdbcLinksRepository.findAll();
//        assertEquals(allVal.size(), linksSize + 1);
//    }
//}
