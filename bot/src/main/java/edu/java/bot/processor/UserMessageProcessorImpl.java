package edu.java.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UntrackCommand;
import java.util.List;

public class UserMessageProcessorImpl implements UserMessageProcessor {
    @Override
    public List<? extends Command> commands() {
        return List.of(new StartCommand(),
                new HelpCommand(), new TrackCommand(),
                new ListCommand(), new UntrackCommand());
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
