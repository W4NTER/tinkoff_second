package edu.java.domain.dto;

import java.time.OffsetDateTime;

public record ChatDTO(Long id, OffsetDateTime createdAt, OffsetDateTime editedAt) {
}
