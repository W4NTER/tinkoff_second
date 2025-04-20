package edu.java.scrapper.domain.repository.jdbc;

import edu.java.domain.repository.TgChatRepository;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class JdbcTgChatRepositoryTest extends IntegrationTest {

    @DynamicPropertySource
    static void setJdbc(DynamicPropertyRegistry registry) {
        registry.add("app.database-access-type", () -> "jdbc");
    }

    @Autowired
    private TgChatRepository tgChatRepository;


    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    void addTest() {
        int sizeChatTable = tgChatRepository.findAll().size();
        tgChatRepository.add(1L);
        var obj = tgChatRepository.findAll();
        System.out.println(obj);
        assertEquals(obj.size(), sizeChatTable + 1);
    }

    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    void deleteTest() {
        int sizeChatTable = tgChatRepository.findAll().size();
        tgChatRepository.add(1L);
        var chat = tgChatRepository.findAll();
        var id = chat.get(0).id();

        assertEquals(sizeChatTable + 1, chat.size()); // проверяем что запись была добавлена
        tgChatRepository.remove(id);
        var sizeAfterRemove = tgChatRepository.findAll().size();
        assertEquals(sizeChatTable, sizeAfterRemove ); // проверяем что запись была удалена
    }

    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    void findAll() {
        int sizeChatTable = tgChatRepository.findAll().size();
        tgChatRepository.add(1L);
        tgChatRepository.add(2L);
        tgChatRepository.add(3L);
        var allVal = tgChatRepository.findAll();
        assertEquals(allVal.size(), sizeChatTable + 3); //использовалось раньше, даже не знаю как его отдельно проверить
    }

}