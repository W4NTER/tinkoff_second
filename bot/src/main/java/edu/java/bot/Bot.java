package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.processor.UserMessageProcessorImpl;
import org.springframework.stereotype.Component;

@Component
public class Bot {
    private TelegramBot bot;
    private ApplicationConfig config;

    public void start() {
        UserMessageProcessorImpl sendMessage = new UserMessageProcessorImpl();
        bot = new TelegramBot(System.getenv("TELEGRAM_API_KEY"));
        bot.setUpdatesListener(updates -> {
            updates.forEach(update -> bot.execute(sendMessage.process(update)));
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
