package edu.java.controller;

import edu.java.controller.dto.request.AddLinkRequest;
import edu.java.controller.dto.response.LinkResponse;
import edu.java.controller.dto.response.ListLinksResponse;
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
    public ResponseEntity<ListLinksResponse> getAllTrackedLinks(@RequestBody ListLinksResponse listLinksResponse) {
        return ResponseEntity.ok().build();
    }


    @PostMapping
    public ResponseEntity<LinkResponse> trackLink(@RequestHeader Long id, @RequestBody AddLinkRequest addLinkRequest) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<LinkResponse> untrackLink(@RequestHeader Long id) {
        return ResponseEntity.ok().build();
    }
}
