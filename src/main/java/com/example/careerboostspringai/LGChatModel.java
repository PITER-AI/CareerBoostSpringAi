package com.example.careerboostspringai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class LGChatModel implements ChatModel {

    private final String API_URL = "http://localhost:8000";
    private final String MODEL_NAME = "LGChat";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    private String getUserQuestion(Prompt prompt) {
        return prompt.getInstructions().stream()
                .filter(message -> !message.getContent().contains("You are PITER"))
                .map(message -> message.getContent())
                .filter(content -> !content.isEmpty())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No valid question found"));
    }

    @Override
    public ChatResponse call(Prompt prompt) {
        // 마지막 사용자 메시지만 추출
        String userQuestion = getUserQuestion(prompt);

        // 요청 생성
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("max_new_tokens", 256);
        parameters.put("temperature", 0.7);
        parameters.put("stream", false);
        parameters.put("do_sample", true);
        parameters.put("top_p", 0.9);
        parameters.put("top_k", 50);
        parameters.put("repetition_penalty", 1.2);
        parameters.put("stop", Arrays.asList("</s>", "\n\n"));

        Map<String, Object> request = new HashMap<>();
        request.put("inputs", "질문: " + userQuestion + "\n답변:");
        request.put("parameters", parameters);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    API_URL,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && !response.getBody().isEmpty()) {
                String content = (String) response.getBody().get(0).get("generated_text");

                if (content != null) {
                    int answerStart = content.indexOf("답변:");
                    if (answerStart != -1) {
                        content = content.substring(answerStart + "답변:".length()).trim();
                    }
                    content = content.replaceAll("\\s*\\.\\s*$", "");
                } else {
                    content = "죄송합니다. 응답을 생성할 수 없습니다.";
                }

                AssistantMessage assistantMessage = new AssistantMessage(content);
                return ChatResponse.builder()
                        .withGenerations(List.of(new Generation(assistantMessage)))
                        .build();
            }

            throw new RuntimeException("Failed to get valid response from LG Chat model");
        } catch (Exception e) {
            throw new RuntimeException("Error calling LG Chat model", e);
        }
    }

    @Override
    public ChatOptions getDefaultOptions() {
        return new ChatOptions() {
            @Override
            public String getModel() {
                return MODEL_NAME;
            }

            @Override
            public Double getFrequencyPenalty() {
                return 0.0;
            }

            @Override
            public Integer getMaxTokens() {
                return 124;
            }

            @Override
            public Double getPresencePenalty() {
                return 0.0;
            }

            @Override
            public List<String> getStopSequences() {
                return Arrays.asList("\n", ".", ".\n");
            }

            @Override
            public Double getTemperature() {
                return 0.7;
            }

            @Override
            public Integer getTopK() {
                return 50;
            }

            @Override
            public Double getTopP() {
                return 0.9;
            }

            @Override
            public ChatOptions copy() {
                return this;
            }
        };
    }
}