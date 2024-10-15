package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ListCommand implements Command {
    @Autowired
    private ScrapperWebClient scrapperWebClient;

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "показать список отслеживаемых ссылок";
    }


    public String post() {
        return "Нет отслеживаемых ссылок";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), trackedLinks(update));
    }

    private String trackedLinks(Update update) {
        var listOfLinks = scrapperWebClient.getAllTrackedLinks(update.message().chat().id());
        if (!listOfLinks.isEmpty()) {
            return String.join("\n", listOfLinks);
        } else {
            return post();
        }
    }
}
