package edu.java.scrapper.domain.repository;

import edu.java.controller.dto.request.AddLinkRequest;
import edu.java.domain.repository.JdbcChatRepository;
import edu.java.domain.repository.JdbcLinksRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class JdbcLinksRepositoryTest {

    @Autowired
    private JdbcLinksRepository jdbcLinksRepository;

    @Autowired
    private JdbcChatRepository jdbcChatRepository;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        jdbcChatRepository.add();
        var chat_id = jdbcChatRepository.findAll().get(0).id();
        jdbcLinksRepository.add(
                new AddLinkRequest(URI.create("https://github.com/")),
                chat_id);
        var obj = jdbcLinksRepository.findAll();
        System.out.println(obj);
        assertEquals(obj.size(), 1);
    }

    @Test
    @Transactional
    @Rollback
    void deleteTest() {
        jdbcChatRepository.add();
        var chat_id = jdbcChatRepository.findAll().get(0).id();
        jdbcLinksRepository.add(
                new AddLinkRequest(URI.create("https://github.com/")),
                chat_id);
        var chat = jdbcLinksRepository.findAll();
        var id = chat.get(0).id();

        assertEquals(chat.size(), 1); // проверяем что запись была добавлена
        jdbcLinksRepository.remove(id);
        var sizeAfterRemove = jdbcLinksRepository.findAll().size();
        assertEquals(sizeAfterRemove, 0); // проверяем что запись была удалена
    }

    @Test
    @Transactional
    @Rollback
    void findAll() {
        jdbcChatRepository.add();
        var chat_id = jdbcChatRepository.findAll().get(0).id();
        jdbcLinksRepository.add(
                new AddLinkRequest(URI.create("https://github.com/")),
                chat_id);
        var allVal = jdbcLinksRepository.findAll();
        assertEquals(allVal.size(), 1);
    }
}
