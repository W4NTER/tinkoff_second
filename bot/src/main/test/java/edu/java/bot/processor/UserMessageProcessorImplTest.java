//package edu.java.bot.processor;
//
//import com.pengrad.telegrambot.model.Chat;
//import com.pengrad.telegrambot.model.Message;
//import com.pengrad.telegrambot.model.Update;
//import com.pengrad.telegrambot.model.User;
//import com.pengrad.telegrambot.request.SendMessage;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//@ExtendWith(MockitoExtension.class)
//class UserMessageProcessorImplTest {
//    @Mock
//    Message message = new Message();
//
//    @Mock
//    Update update = new Update();
//
//    @Mock
//    Chat chat = new Chat();
//
//    @Test
//    @DisplayName("Вывод сообщения при неизвестной команде")
//    void testThatMessageWithWrongCommandReturnedSucceed() {
//        Mockito.when(update.message()).thenReturn(message);
//        Mockito.when(message.text()).thenReturn("some text");
//        Mockito.when(message.chat()).thenReturn(chat);
//        Mockito.when(chat.id()).thenReturn(1L);
//        UserMessageProcessorImpl userProcessor = new UserMessageProcessorImpl();
//
//        var res = userProcessor.process(update);
//
//        var expectedRes = new SendMessage(1, "Такой команды не существует").getParameters().get("text");
//        assertEquals(res.getParameters().get("text"), expectedRes);
//    }
//}
