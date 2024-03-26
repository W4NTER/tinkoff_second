package edu.java.scrapper.domain.repository;

import edu.java.domain.repository.jdbc.JdbcLinksRepository;
import edu.java.domain.repository.jdbc.JdbcTgChatRepository;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class JdbcLinksRepositoryTest extends IntegrationTest {

    @Autowired
    private JdbcLinksRepository jdbcLinksRepository;

    @Autowired
    private JdbcTgChatRepository jdbcChatRepository;

    private final static Long CHAT_ID = 1L;
    private final static URI URL = URI.create("https://github.com/");

    @Test
    @Transactional
    @Rollback
    void addTest() {
        jdbcChatRepository.add(CHAT_ID);
        jdbcLinksRepository.add(URL, CHAT_ID);
        var obj = jdbcLinksRepository.findAll(CHAT_ID);
        System.out.println(obj);
        assertEquals(obj.size(), 1);
    }

    @Test
    @Transactional
    @Rollback
    void deleteTest() {
        jdbcChatRepository.add(CHAT_ID);
        jdbcLinksRepository.add(URL, CHAT_ID);
        var chat = jdbcLinksRepository.findAll(CHAT_ID);
        var id = chat.get(0).id();

        assertEquals(chat.size(), 1); // проверяем что запись была добавлена
        jdbcLinksRepository.remove(URL, CHAT_ID);
        var sizeAfterRemove = jdbcLinksRepository.findAll(CHAT_ID).size();
        assertEquals(sizeAfterRemove, 0); // проверяем что запись была удалена
    }

    @Test
    @Transactional
    @Rollback
    void findAll() {
        jdbcChatRepository.add(CHAT_ID);
        jdbcLinksRepository.add(URL, CHAT_ID);
        var allVal = jdbcLinksRepository.findAll(CHAT_ID);
        assertEquals(allVal.size(), 1);
    }
}
