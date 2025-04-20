package edu.java.bot.controller;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.bot.BotImpl;
import edu.java.bot.controller.dto.request.LinkUpdateRequest;
import edu.java.bot.controller.exception.IdNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController {
    @PostMapping("/updates")
    public ResponseEntity<Void> processUpdates(@RequestBody LinkUpdateRequest linkUpdateRequest) {
        if (linkUpdateRequest.tgChatIds().size() == 0) {
            throw new  IdNotFoundException("У ссыдки нет ни одного связанного с ней чата");
        }
        BotImpl bot = new BotImpl();
        linkUpdateRequest.tgChatIds().stream().forEach(chatId ->
                bot.execute(new SendMessage(chatId,
                        linkUpdateRequest.description() + linkUpdateRequest.url())));

        return ResponseEntity.ok().build();
    }
}
