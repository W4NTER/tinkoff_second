package edu.java.bot.controller.dto.request;

import java.util.List;

public record LinkUpdateRequest(
        Long id,
        String url,
        String description,
//        List<Long> tgChatIds
        Long tgChatId //поменял под связь 1 ко многим,
        // могу изменить,
        // если все таки правильней будет сделать связь многие ко многим
) {}
