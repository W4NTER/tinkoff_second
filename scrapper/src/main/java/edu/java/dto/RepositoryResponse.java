package edu.java.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

//@Getter
//@Setter
//public class RepositoryResponse {
//    @JsonProperty("id")
//    Long id;
//
//    @JsonProperty("full_name")
//    String name;
//
//    @JsonProperty("last_update")
//    OffsetDateTime updatedAt;
//}

@JsonIgnoreProperties(ignoreUnknown = true)
public record RepositoryResponse(
    @JsonProperty("id")
    Long id,

    @JsonProperty("full_name")
    String name,

    @JsonProperty("updated_at")
    OffsetDateTime updatedAt
) {}

