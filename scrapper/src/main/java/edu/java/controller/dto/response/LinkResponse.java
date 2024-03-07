package edu.java.controller.dto.response;

import java.net.URI;

public record LinkResponse(
        Long id,
        URI url
) {}
