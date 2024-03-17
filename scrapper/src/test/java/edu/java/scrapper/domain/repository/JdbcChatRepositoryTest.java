package edu.java.scrapper.domain.repository;

import edu.java.domain.repository.JdbcChatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class JdbcChatRepositoryTest {


    @Autowired
    private JdbcChatRepository jdbcChatRepository;


    @Test
    @Transactional
    @Rollback
    void addTest() {
        jdbcChatRepository.add();
        var obj = jdbcChatRepository.findAll();
        System.out.println(obj);
        assertEquals(obj.size(), 1);
    }

    @Test
    @Transactional
    @Rollback
    void deleteTest() {
        jdbcChatRepository.add();
        var chat = jdbcChatRepository.findAll();
        var id = chat.get(0).id();

        assertEquals(chat.size(), 1); // проверяем что запись была добавлена
        jdbcChatRepository.remove(id);
        var sizeAfterRemove = jdbcChatRepository.findAll().size();
        assertEquals(sizeAfterRemove, 0); // проверяем что запись была удалена
    }

    @Test
    @Transactional
    @Rollback
    void findAll() {
        jdbcChatRepository.add();
        jdbcChatRepository.add();
        jdbcChatRepository.add();
        var allVal = jdbcChatRepository.findAll();
        assertEquals(allVal.size(), 3); //использовалось раньше, даже не знаю как его отдельно проверить
    }

}
