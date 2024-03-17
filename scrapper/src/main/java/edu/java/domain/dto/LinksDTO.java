package edu.java.domain.dto;

import java.net.URI;
import java.time.OffsetDateTime;

public record LinksDTO(
        Long id,
        URI link,
        Long chatId,
        OffsetDateTime createdAt,
        OffsetDateTime editedAt
){}
