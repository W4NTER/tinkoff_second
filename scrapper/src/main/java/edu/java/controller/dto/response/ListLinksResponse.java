package edu.java.controller.dto.response;

public record ListLinksResponse(
        LinkResponse links,
        int size
) {
}
