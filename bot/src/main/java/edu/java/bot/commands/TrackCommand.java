package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperWebClient;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command {
    @Autowired
    ScrapperWebClient scrapperWebClient;

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "начать отслеживание ссылки";
    }

    @Override
    public String post() {
        return description();
    }

    @Override
    public boolean supports() {
        return true;
    }

    @Override
    public SendMessage handle(Update update) {
        scrapperWebClient.trackLink(update.message().chat().id(), URI.create(update.message().text().split(" ")[1]));
        return new SendMessage(update.message().chat().id(), post());
    }
}
