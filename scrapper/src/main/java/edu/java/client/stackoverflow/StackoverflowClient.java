package edu.java.client.stackoverflow;

import edu.java.client.stackoverflow.dto.StackoverflowResponseDTO;

public interface StackoverflowClient {
    StackoverflowResponseDTO getLastActivity(String link);
}
