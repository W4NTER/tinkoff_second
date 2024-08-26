package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.ApplicationContextProvider;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "вывести окно с командами";
    }

    public List<Command> getListCommands() {
        var mapOfCommands = ApplicationContextProvider.getApplicationContext().getBeansOfType(Command.class);
        return new ArrayList<>(mapOfCommands.values());
    }

    @Override
    public String post() {
        var listCommands = getListCommands();
        StringBuilder builder = new StringBuilder("Список команд, которые знает этот бот:");
        for (Command value : listCommands) {
            builder.append(String.format("\n%s - %s", value.command(), value.description()));
        }
        return builder.toString();
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), post());
    }
}
