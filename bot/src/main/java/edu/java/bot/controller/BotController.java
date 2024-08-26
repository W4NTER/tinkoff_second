package edu.java.bot.controller;

import edu.java.bot.controller.dto.request.LinkUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController {

    @PostMapping("/updates")
    public ResponseEntity<Void> processUpdates(@RequestBody LinkUpdateRequest linkUpdateRequest) {
        return ResponseEntity.ok().build();
    }
}
