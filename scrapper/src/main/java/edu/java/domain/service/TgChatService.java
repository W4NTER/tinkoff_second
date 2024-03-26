package edu.java.domain.service;

public interface TgChatService {
    void register(Long thChatId);

    void removeUser(Long tgChatId);
}
