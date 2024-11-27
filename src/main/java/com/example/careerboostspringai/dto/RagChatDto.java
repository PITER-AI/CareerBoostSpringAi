package com.example.careerboostspringai.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RagChatDto {
    private Long chatId;
    private MessageDto messages;

    public RagChatDto(Long chatId, MessageDto messages) {
        this.chatId = chatId;
        this.messages = messages;
    }
}