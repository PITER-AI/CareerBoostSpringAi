package com.example.careerboostspringai.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RagChatResponse {
    private final String status;
    private final Long chatId;
    private final MessageDto message;
    private final String error;

    // 성공 응답을 위한 정적 팩토리 메서드
    public static RagChatResponse success(Long chatId, MessageDto message) {
        return RagChatResponse.builder()
                .status("success")
                .chatId(chatId)
                .message(message)
                .build();
    }

    // 에러 응답을 위한 정적 팩토리 메서드
    public static RagChatResponse error(String errorMessage) {
        return RagChatResponse.builder()
                .status("error")
                .error(errorMessage)
                .build();
    }
}