package edu.java.scrapper.domain.repository;

import edu.java.domain.repository.jdbc.JdbcTgChatRepository;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@SpringBootTest

public class JdbcTgChatRepositoryTest extends IntegrationTest {
    @Autowired
    private JdbcTgChatRepository jdbcChatRepository;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        int sizeChatTable = jdbcChatRepository.findAll().size();
        jdbcChatRepository.add(1L);
        var obj = jdbcChatRepository.findAll();
        System.out.println(obj);
        assertEquals(obj.size(), sizeChatTable + 1);
    }

    @Test
    @Transactional
    @Rollback
    void deleteTest() {
        int sizeChatTable = jdbcChatRepository.findAll().size();
        jdbcChatRepository.add(1L);
        var chat = jdbcChatRepository.findAll();
        var id = chat.get(0).id();

        assertEquals(sizeChatTable + 1, chat.size()); // проверяем что запись была добавлена
        jdbcChatRepository.remove(id);
        var sizeAfterRemove = jdbcChatRepository.findAll().size();
        assertEquals(sizeChatTable, sizeAfterRemove ); // проверяем что запись была удалена
    }

    @Test
    @Transactional
    @Rollback
    void findAll() {
        int sizeChatTable = jdbcChatRepository.findAll().size();
        jdbcChatRepository.add(1L);
        jdbcChatRepository.add(2L);
        jdbcChatRepository.add(3L);
        var allVal = jdbcChatRepository.findAll();
        assertEquals(allVal.size(), sizeChatTable + 3); //использовалось раньше, даже не знаю как его отдельно проверить
    }

}