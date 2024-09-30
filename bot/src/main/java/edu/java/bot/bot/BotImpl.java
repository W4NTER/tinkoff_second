package edu.java.bot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.processor.UserMessageProcessorImpl;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class BotImpl implements Bot {

    private final TelegramBot bot = new TelegramBot(System.getenv("TELEGRAM_API_KEY"));
    private final static Logger LOGGER = LogManager.getLogger();

    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        UserMessageProcessorImpl user = new UserMessageProcessorImpl();
        for (Update update : updates) {
                bot.execute(user.process(update));
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void start() {
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
