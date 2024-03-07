package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StartCommandTest {

    @Mock
    Update update = new Update();

    @Mock
    Message message = new Message();

    @Mock
    Chat chat = new Chat();

    @Test
    @DisplayName("Проверка правильного ответа")
    void testThatCheckNormalAnswerByStartCommandReturnedSucceed() {
        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(1L);

        StartCommand command = new StartCommand();
        String result = (String) command.handle(update).getParameters().get("text");

        String EXPECTED_VALUE = (String) new SendMessage(1,
                "Этот бот призван стать вашим единым центром уведомлений, чтобы узнать все команды, введите /help")
                .getParameters().get("text");
        assertEquals(result, EXPECTED_VALUE);
    }
}
