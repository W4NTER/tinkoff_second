package edu.java.controller;

import edu.java.service.TgChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tg-chat/{id}")
public class TelegramChatController {
    private final TgChatService tgChatService;

    public TelegramChatController(TgChatService tgChatService) {
        this.tgChatService = tgChatService;
    }

    @PostMapping
    public ResponseEntity<Void> registerChat(@PathVariable Long id) {
        tgChatService.register(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        tgChatService.removeUser(id);
        return ResponseEntity.ok().build();
    }
}
