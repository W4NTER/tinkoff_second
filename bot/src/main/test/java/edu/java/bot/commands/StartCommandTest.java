package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperWebClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class StartCommandTest {

    @Mock
    Update update = new Update();

    @Mock
    Message message = new Message();

    @Mock
    Chat chat = new Chat();

    @Mock
    ScrapperWebClient scrapperWebClient;

    @InjectMocks
    StartCommand command;


    @Test
    @DisplayName("Проверка правильного ответа")
    void testThatCheckNormalAnswerByStartCommandReturnedSucceed() {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(1L);

        String result = (String) command.handle(update).getParameters().get("text");

        String expectedValue =
                "Этот бот призван стать вашим единым центром уведомлений, чтобы узнать все команды, введите /help";
        assertEquals(result, expectedValue);
    }
}
