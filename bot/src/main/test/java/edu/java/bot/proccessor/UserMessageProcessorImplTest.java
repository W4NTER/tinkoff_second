package edu.java.bot.proccessor;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperWebClient;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.processor.UserMessageProcessorImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserMessageProcessorImplTest {
    private final static String FINAL_OUTPUT_BY_HELP_COMMAND = """
            Список команд, которые знает этот бот:
            /help - вывести окно с командами
            /list - показать список отслеживаемых ссылок
            /start - зарегистрировать пользователя
            /track - начать отслеживание ссылки
            /untrack - прекратить отслеживание ссылки""";

    @Mock
    Update update = new Update();

    @Mock
    Chat chat = new Chat();

    @Mock
    Message message = new Message();

    @Test
    @DisplayName("Проверка вывода строки о несуществующей команде")
    void testThatProcessUnexpectedCommandReturnedSucceed() {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(message.text()).thenReturn("some test");
        Mockito.when(chat.id()).thenReturn(1L);

        UserMessageProcessorImpl processor = new UserMessageProcessorImpl();
        var res = processor.process(update);

        String expectedValue = "Такой команды не существует";
        Assertions.assertEquals(res.getParameters().get("text"), expectedValue);
    }

    @Test
    @DisplayName("Проверка правильеого вывода существующей команды")
    void testThatProcessExpectedCommandReturnedSucceed() {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(message.text()).thenReturn("/help");
        Mockito.when(chat.id()).thenReturn(1L);

        UserMessageProcessorImpl processor = new UserMessageProcessorImpl();
        var res = processor.process(update);

        Assertions.assertEquals(res.getParameters().get("text"), FINAL_OUTPUT_BY_HELP_COMMAND);
    }
}
