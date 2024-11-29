package com.example.careerboostspringai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.messages.AssistantMessage;
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

@Component("lgChatModel")
public class LGChatModel implements ChatModel {

    private final String API_URL = "http://localhost:8000";
    private final String MODEL_NAME = "LGChat";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ChatResponse call(Prompt prompt) {
        // 메시지 내용을 하나의 문자열로 결합
        StringBuilder inputsBuilder = new StringBuilder();
        for (var instruction : prompt.getInstructions()) {
            inputsBuilder.append(instruction.getContent()).append(" ");
        }
        String inputs = inputsBuilder.toString().trim();

        // 요청 생성
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("max_new_tokens", 124);
        parameters.put("temperature", 0.7);
        parameters.put("stream", false);
        parameters.put("do_sample", false);
        parameters.put("top_p", 0.9);
        parameters.put("top_k", 50);
        parameters.put("repetition_penalty", 1.2);
        parameters.put("stop", Arrays.asList("\n", ".", ".\n"));

        Map<String, Object> request = new HashMap<>();
        request.put("inputs", inputs);
        request.put("parameters", parameters);

        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // 응답 타입을 List.class로 변경
//        ResponseEntity<List> response = restTemplate.exchange(
//                API_URL,
//                HttpMethod.POST,
//                entity,
//                List.class
//        );
//
//        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//            // 첫 번째 응답 요소를 가져옴
//            List<?> responseBody = response.getBody();
//            if (!responseBody.isEmpty()) {
//                Object firstResponse = responseBody.get(0);
//
//                // Map으로 캐스팅하여 처리
//                if (firstResponse instanceof Map) {
//                    @SuppressWarnings("unchecked")
//                    Map<String, Object> responseMap = (Map<String, Object>) firstResponse;
//                    String content = (String) responseMap.get("generated_text");
//                    if (content == null) {
//                        content = "No response generated";
//                    }
//                    AssistantMessage assistantMessage = new AssistantMessage(content);
//                    return ChatResponse.builder()
//                            .withGenerations(List.of(new Generation(assistantMessage)))
//                            .build();
//                }
//            }
//        }

        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                API_URL,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List<Map<String, Object>> responseBody = response.getBody();
            if (!responseBody.isEmpty()) {
                Map<String, Object> firstResponse = responseBody.get(0);
                String content = (String) firstResponse.get("generated_text");

                // null 체크 및 기본값 설정
                if (content == null || content.trim().isEmpty()) {
                    content = "No response generated";
                }

                AssistantMessage assistantMessage = new AssistantMessage(content);
                return ChatResponse.builder()
                        .withGenerations(List.of(new Generation(assistantMessage)))
                        .build();
            }
        }

        throw new RuntimeException("Failed to get valid response from LG Chat model");
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