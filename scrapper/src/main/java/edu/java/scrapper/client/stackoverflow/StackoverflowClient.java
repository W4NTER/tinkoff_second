package edu.java.scrapper.client.stackoverflow;

import edu.java.scrapper.client.stackoverflow.dto.StackoverflowResponseDTO;

public interface StackoverflowClient {
    StackoverflowResponseDTO getLastActivity(String link);
}
