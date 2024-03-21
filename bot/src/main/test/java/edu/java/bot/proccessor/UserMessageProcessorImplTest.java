package edu.java.bot.proccessor;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.processor.UserMessageProcessorImpl;
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
public class UserMessageProcessorImplTest {

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
        Mockito.when(message.text()).thenReturn("/start");
        Mockito.when(chat.id()).thenReturn(1L);

        UserMessageProcessorImpl processor = new UserMessageProcessorImpl();
        var res = processor.process(update);

        String expectedValue = "Этот бот призван стать вашим единым центром уведомлений, чтобы узнать все команды, введите /help";
        Assertions.assertEquals(res.getParameters().get("text"), expectedValue);
    }
}
