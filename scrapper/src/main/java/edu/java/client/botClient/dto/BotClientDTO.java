package edu.java.client.botClient.dto;

public record BotClientDTO(Long id, String url, String description, Long[] tgChatIds) {
}
