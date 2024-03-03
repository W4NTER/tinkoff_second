package edu.java.client.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StackOverFlowResponseDTO(
    Long id,
    String question,
    OffsetDateTime lastEditDate
) {
}
