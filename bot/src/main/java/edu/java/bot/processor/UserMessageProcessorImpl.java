package edu.java.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.ApplicationContextProvider;
import edu.java.bot.commands.Command;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;


@Component
public class UserMessageProcessorImpl implements UserMessageProcessor {
    private boolean supportsInput;

    @Override
    public List<? extends Command> commands() {
        var mapOfCommands = ApplicationContextProvider.getApplicationContext().getBeansOfType(Command.class);
        return new ArrayList<>(mapOfCommands.values());

    }

    @Override
    public SendMessage process(Update update) {
        var command = getCommand(update);
        if (command.isEmpty()) {
            return new SendMessage(update.message().chat().id(), "Такой команды не существует");
        }
        return command.get().handle(update);
    }

    public Optional<? extends Command> getCommand(Update update) {
        return commands().stream().filter(cmd ->
                cmd.command().equals(update.message().text().split(" ")[0])).findFirst();
    }

    public void setSupportsInput(Update update) {
        var command = getCommand(update);
        command.ifPresent(value -> this.supportsInput = value.supports());
    }

    public boolean isSupportsInput(Update update) {
        setSupportsInput(update);
        return supportsInput;
    }
}
