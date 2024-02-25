package edu.java.bot.processor;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.ApplicationContextProvider;
import edu.java.bot.commands.Command;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;


@Service
public class UserMessageProcessorImpl implements UserMessageProcessor {
    @Override
    public List<? extends Command> commands() {
        Map<String, Command> map  = ApplicationContextProvider.getApplicationContext().getBeansOfType(Command.class);
        return new ArrayList<>(map.values());
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
