package edu.java.scrapper.client.github;

import edu.java.scrapper.client.github.dto.GitResponseDTO;

public interface GitHubClient {
    GitResponseDTO getLastUpdate(String userName, String repo);
}
