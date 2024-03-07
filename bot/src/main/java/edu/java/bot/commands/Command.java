package edu.java.bot.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public interface Command {
    String command();

    String description();

    String post();

    SendMessage handle(Update update);

    default BotCommand toApiCommand() {
        return new BotCommand(command(), post());
    }
}
