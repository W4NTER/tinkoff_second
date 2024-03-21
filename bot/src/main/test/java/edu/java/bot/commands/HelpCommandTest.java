package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class HelpCommandTest {

    @Mock
    Update update = new Update();

    @Mock
    Message message = new Message();

    @Mock
    Chat chat = new Chat();

    @Test
    @DisplayName("Проверка билда строки для отображения команд")
    void testThatMethodPostSendCorrectStringReturnedSucceed() {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(1L);

        HelpCommand command = new HelpCommand();
        String result = (String) command.handle(update).getParameters().get("text");

        String expectedValue = """
                Список команд, которые знает этот бот:
                /help - вывести окно с командами
                /list - показать список отслеживаемых ссылок
                /start - зарегистрировать пользователя
                /track - начать отслеживание ссылки
                /untrack - прекратить отслеживание ссылки""";
        Assertions.assertEquals(result, expectedValue);
    }
}
