package edu.java.scrapper.client.stackoverflow.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;




public record StackoverflowResponseDTO(
    @JsonProperty("question_id")
    Long questionId,
    @JsonProperty("title")
    String questionTitle,
    @JsonProperty("last_activity_date")
    OffsetDateTime lastActivityDate
) {
}

