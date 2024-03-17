package edu.java.controller;

import edu.java.controller.dto.request.AddLinkRequest;
import edu.java.controller.dto.response.LinkResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/links")
public class LinksController {

    @GetMapping
    public ResponseEntity<Void> getAllTrackedLinks() {
        return ResponseEntity.ok().build();
    }


    @PostMapping
    public ResponseEntity<LinkResponse> trackLink(
            @RequestHeader("Tg-Chat-Id") Long id,
            @RequestBody AddLinkRequest addLinkRequest) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<LinkResponse> untrackLink(@RequestHeader("Tg-Chat-Id") Long id) {
        return ResponseEntity.ok().build();
    }
}
