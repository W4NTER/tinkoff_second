package edu.java.client.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitResponseDTO(
    @JsonProperty("id")
    Long id,

    @JsonProperty("full_name")
    String name,

    @JsonProperty("updated_at")
    OffsetDateTime updatedAt
) {}

