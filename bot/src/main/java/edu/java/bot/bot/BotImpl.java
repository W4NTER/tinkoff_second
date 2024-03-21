package edu.java.bot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.processor.UserMessageProcessorImpl;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class BotImpl implements Bot {
    private TelegramBot bot;
    private Long customerId;
    private boolean linkCommand;
    private final static Logger LOGGER = LogManager.getLogger();

    @Override
    public int process(List<Update> updates) {
        UserMessageProcessorImpl user = new UserMessageProcessorImpl();
        for (Update update : updates) {
            if (user.isSupportsInput(update)) {
                linkCommand = true;
            }
            if (linkCommand && update.message().chat().id().equals(customerId)) {
                String link = update.message().text();
                bot.execute(new SendMessage(update.message().chat().id(), "Ваша ссылка: " + link));
                linkCommand = false;
            } else {
                bot.execute(user.process(update));
            }
            customerId = update.message().chat().id();
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void start() {
        bot = new TelegramBot(System.getenv("TELEGRAM_API_KEY"));
        bot.setUpdatesListener(this, exception -> {
            if (exception.response() != null) {
                // got bad response from telegram
                exception.response().errorCode();
                exception.response().description();
            } else {
                // probably network error
                LOGGER.info(exception.getMessage());
            }
        });
    }

    @Override
    public void close() {

    }
}
