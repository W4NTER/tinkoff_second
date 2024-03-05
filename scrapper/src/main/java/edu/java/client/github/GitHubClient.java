package edu.java.client.github;

import edu.java.client.github.dto.GitResponseDTO;

public interface GitHubClient {
    GitResponseDTO getLastUpdate(String userName, String repo);
}
