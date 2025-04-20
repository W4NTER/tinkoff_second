package edu.java.service;

public interface TgChatService {
    void register(Long tgChatId);

    void removeUser(Long tgChatId);
}
