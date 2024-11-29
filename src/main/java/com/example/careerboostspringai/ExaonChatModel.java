package com.example.careerboostspringai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.ai.chat.messages.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.ai.chat.prompt.Prompt.*;

//@Component
//public class ExaonChatModel implements ChatModel {
//
//    private final String API_URL = "http://localhost:8000"; // 엑사온 API URL
//    private final RestTemplate restTemplate = new RestTemplate();
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//
//    @Override
//    public ChatResponse call(Prompt prompt) {
//        // 메시지 내용을 단일 문자열로 합치기
//        String userInput = prompt.getInstructions().stream()
//                .map(Message::getContent)
//                .collect(Collectors.joining(" "));
//
//        // 요청 데이터 생성
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("inputs", userInput); // 단일 문자열로 입력
//        requestBody.put("parameters", Map.of(
//                "temperature", 0.7,
//                "top_p", 0.95,
//                "max_new_tokens", 256
//        ));
//
//        // JSON 직렬화
//        String requestJson;
//        try {
//            requestJson = objectMapper.writeValueAsString(requestBody);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Failed to serialize request body", e);
//        }
//
//        // HTTP 요청
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
//
//        ResponseEntity<Map> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, Map.class);
//
//        // 응답 처리
//        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//            String content = (String) response.getBody().get("content");
//            AssistantMessage assistantMessage = new AssistantMessage(content);
//            return ChatResponse.builder()
//                    .withGenerations(List.of(new Generation(assistantMessage)))
//                    .build();
//        } else {
//            throw new RuntimeException("Failed to get valid response from Exaon API");
//        }
//    }
//
//    @Override
//    public ChatOptions getDefaultOptions() {
//        return new ChatOptions() {
//            @Override
//            public Double getTemperature() {
//                return 0.7;
//            }
//
//            @Override
//            public Integer getTopK() {
//                return null;
//            }
//
//            @Override
//            public Double getTopP() {
//                return 0.95;
//            }
//
//            @Override
//            public String getModel() {
//                return null;
//            }
//
//            @Override
//            public Double getFrequencyPenalty() {
//                return null;
//            }
//
//            @Override
//            public Integer getMaxTokens() {
//                return 256;
//            }
//
//            @Override
//            public Double getPresencePenalty() {
//                return null;
//            }
//
//            @Override
//            public List<String> getStopSequences() {
//                return null;
//            }
//
//            @Override
//            public ChatOptions copy() {
//                return this;
//            }
//        };
//    }
//
//    @Getter
//    public static class Request {
//        private final List<Param> inputs;
//
//        public Request(List<Param> inputs) {
//            this.inputs = inputs;
//        }
//
//        @Getter
//        public static class Param {
//            private final List<Message> utterances;
//            private final ChatOptions parameters;
//
//            public Param(List<Message> utterances, ChatOptions parameters) {
//                this.utterances = utterances;
//                this.parameters = parameters;
//            }
//        }
//
//        @Getter
//        public static class Message {
//            public static final String ROLE_SYSTEM = "system";
//            public static final String ROLE_USER = "user";
//            public static final String ROLE_ASSISTANT = "assistant";
//
//            private final String role;
//            private final String content;
//
//            public Message(String role, String content) {
//                this.role = role;
//                this.content = content;
//            }
//        }
//    }
//}

