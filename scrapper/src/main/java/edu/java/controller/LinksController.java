package edu.java.controller;

import edu.java.controller.dto.request.AddLinkRequest;
import edu.java.controller.dto.request.RemoveLinkRequest;
import edu.java.controller.dto.response.LinkResponse;
import edu.java.domain.dto.LinksDTO;
import edu.java.domain.service.jdbc.JdbcLinksService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Autowired
    private JdbcLinksService jdbcLinksService;

    @GetMapping
    public ResponseEntity<List<LinksDTO>> getAllTrackedLinks(@RequestHeader("Tg-Chat-Id") Long id) {
        return new ResponseEntity<>(jdbcLinksService.listAll(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LinkResponse> trackLink(
            @RequestHeader("Tg-Chat-Id") Long chatId,
            @RequestBody AddLinkRequest addLinkRequest) {
        if (addLinkRequest.link() == null) {
            throw new IllegalArgumentException("Ссылка равна null");
        }
        LinksDTO linksDTO = jdbcLinksService.addLink(chatId, addLinkRequest.link());
        return new ResponseEntity<>(new LinkResponse(linksDTO.id(), linksDTO.link()), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<LinkResponse> untrackLink(
            @RequestHeader("Tg-Chat-Id") Long chatId,
            @RequestBody RemoveLinkRequest url) {
        LinksDTO linksDTO = jdbcLinksService.deleteLink(chatId, url.link());
        return new ResponseEntity<>(new LinkResponse(linksDTO.id(), linksDTO.link()), HttpStatus.OK);
    }
}
