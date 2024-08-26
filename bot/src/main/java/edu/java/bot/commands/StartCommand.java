package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
    @Autowired
    ScrapperWebClient scrapperWebClient;

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "зарегистрировать пользователя";
    }

    @Override
    public String post() {
        return "Этот бот призван стать вашим единым центром уведомлений, чтобы узнать все команды, введите /help";
    }

    @Override
    public SendMessage handle(Update update) {
        scrapperWebClient.registerChat(update.message().chat().id());
        return new SendMessage(update.message().chat().id(), post());
    }
}
