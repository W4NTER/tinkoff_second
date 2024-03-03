package edu.java.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GitResponseDTO(
    @JsonProperty("id")
    Long id,

    @JsonProperty("full_name")
    String name,

    @JsonProperty("updated_at")
    OffsetDateTime updatedAt
) {}

