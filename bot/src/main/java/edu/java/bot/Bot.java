package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.processor.UserMessageProcessorImpl;
import java.util.List;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(ApplicationConfig.class)
public class Bot {
    private TelegramBot bot;
//    private ApplicationConfig config;


    public int process(List<Update> updates) {
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void start() {
        UserMessageProcessorImpl sendMessage = new UserMessageProcessorImpl();
        bot = new TelegramBot(System.getenv("TELEGRAM_API_KEY"));
        bot.setUpdatesListener(updates -> {
            updates.forEach(update -> bot.execute(sendMessage.process(update)));
            return process(updates);
        });
    }
}
