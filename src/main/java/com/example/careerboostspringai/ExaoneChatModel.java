package com.example.careerboostspringai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ExaoneChatModel implements ChatModel {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public ExaoneChatModel() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8000")
                .build();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public ChatResponse call(Prompt prompt) {
        // Prompt에서 메시지 내용 추출
        String userMessage = prompt.toString(); // 또는 prompt.getInput()
        //String userMessage = prompt.getMessages().stream()
          //      .map(Message::getContent)
            //    .collect(Collectors.joining(" "));

        // LPU 서버 요청 형식으로 변환
        Map<String, Object> request = new HashMap<>();
        request.put("inputs", userMessage);
        request.put("parameters", Map.of(
                "max_new_tokens", 256,
                "temperature", 0.7,
                "stream", false
        ));

        try {
            // LPU 서버 호출
            String response = webClient.post()
                    .uri("/generate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // 응답 파싱
            JsonNode responseNode = objectMapper.readTree(response);
            String generatedText = responseNode.get(0)
                    .get("generated_text")
                    .asText();

            // Generation 객체 생성
            AssistantMessage assistantMessage = new AssistantMessage(generatedText);
            Generation generation = new Generation(assistantMessage);

            // ChatResponse 반환
            return new ChatResponse(Collections.singletonList(generation));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate response from Exaone model", e);
        }
    }


    @Override
    public ChatOptions getDefaultOptions() {
        return new CustomChatOptions.Builder()
                .temperature(0.7)
                .maxTokens(256)
                .build();
    }
}