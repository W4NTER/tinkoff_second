package edu.java.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.ApplicationContextProvider;
import edu.java.bot.commands.Command;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;


@Component
public class UserMessageProcessorImpl implements UserMessageProcessor {

    @Override
    public List<? extends Command> commands() {
        var mapOfCommands = ApplicationContextProvider.getApplicationContext().getBeansOfType(Command.class);
        return new ArrayList<>(mapOfCommands.values());

    }

    @Override
    public SendMessage process(Update update) {
        var res = commands().stream().filter(cmd ->
                cmd.command().equals(update.message().text())).findFirst();
        if (res.isPresent()) {
            return res.get().handle(update);
        } else {
            return new SendMessage(update.message().chat().id(), "Такой команды не существует");
        }
    }
}
