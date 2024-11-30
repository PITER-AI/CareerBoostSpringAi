package com.example.careerboostspringai.service;

import com.example.careerboostspringai.dto.ChatDataDto;
import com.example.careerboostspringai.dto.MessageDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class RagService {
    private final ChatClient lgChatClient;

    public RagOutputDto getRagOutPut(ChatDataDto chatDataDto) {
        try {
            if (chatDataDto.getMessages() == null || chatDataDto.getMessages().isEmpty()) {
                throw new IllegalArgumentException("Messages cannot be empty");
            }

            MessageDto lastMessage = chatDataDto.getMessages().get(chatDataDto.getMessages().size() - 1);
            List<Message> chatHistory = convertToChatHistory(chatDataDto.getMessages());
            chatHistory.remove(chatHistory.size() - 1);

            ChatResponse chatResponse = lgChatClient
                    .prompt()
                    .messages(chatHistory)
                    .user(lastMessage.getContent())
                    .call()
                    .chatResponse();

            return new RagOutputDto(chatResponse.getResult().getOutput().getContent());
        } catch (Exception e) {
            log.error("Error in RAG service: ", e);
            throw new RagServiceException("Failed to process chat request", e);
        }
    }

    // convertToChatHistory 메서드는 그대로 유지


    private List<Message> convertToChatHistory(List<MessageDto> messages) {
        return messages.stream()
                .map(message -> switch (message.getRole()) {
                    case "user" -> new UserMessage(message.getContent());
                    case "assistant" -> new AssistantMessage(message.getContent());
                    default -> throw new IllegalArgumentException("Unexpected role: " + message.getRole());
                })
                .collect(Collectors.toList());
    }

    private ChatResponse callChatClient(ChatClient chatClient, List<Message> chatHistory, String userContent) {
        try {
            return chatClient
                    .prompt()
                    .messages(chatHistory)
                    .user(userContent)  // "question: " 프리픽스 제거
                    .call()
                    .chatResponse();
        } catch (Exception e) {
            log.error("ChatClient call failed: ", e);
            throw new ChatClientException("Failed to get response from chat client", e);
        }
    }

    @Getter
    public static class RagServiceException extends RuntimeException {
        public RagServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    @Getter
    public static class ChatClientException extends RuntimeException {
        public ChatClientException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static record RagOutputDto(String content) {}
}