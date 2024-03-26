package edu.java.controller;

import edu.java.domain.service.jdbc.JdbcTgChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tg-chat/{id}")
public class TelegramChatController {
    @Autowired
    private JdbcTgChatService jdbcTgChatService;

    @PostMapping
    public ResponseEntity<Void> registerChat(@PathVariable Long id) {
        jdbcTgChatService.register(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteChat(@PathVariable Long id) {
        jdbcTgChatService.removeUser(id);
        return ResponseEntity.ok().build();
    }
}
