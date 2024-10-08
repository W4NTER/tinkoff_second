package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;


@Component
public class ListCommand implements Command {
    private List<String> trackedLinks = new ArrayList<>();

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "показать список отслеживаемых ссылок";
    }


    public String post() {
        //List of links
        if (!trackedLinks.isEmpty()) {
            return String.join("\n", trackedLinks);
        } else {
            return "Нет отслеживаемых ссылок";
        }
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), post());
    }
}
