package edu.java.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class UserMessageProcessorImpl implements UserMessageProcessor {

    @Autowired
    private ApplicationContext context;

    @Override
    public List<? extends Command> commands() {
        return (List<? extends Command>) context.getBeansOfType(Command.class).values();
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
