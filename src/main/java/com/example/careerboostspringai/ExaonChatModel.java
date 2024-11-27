package com.example.careerboostspringai;

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
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ExaonChatModel implements ChatModel {

    private final String API_URL;
    private final String API_KEY;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    public ExaonChatModel(@Value("${exaon.api.url}") String apiUrl,
                          @Value("${exaon.api.key}") String apiKey) {
        this.API_URL = apiUrl;
        this.API_KEY = apiKey;
    }

    @Override
    public ChatResponse call(Prompt prompt) {
        try {
            String requestBody = objectMapper.writeValueAsString(prompt);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + API_KEY);

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                String content = response.getBody();
                AssistantMessage assistantMessage = new AssistantMessage(content);
                return ChatResponse.builder()
                        .withGenerations(List.of(new Generation(assistantMessage)))
                        .build();
            } else {
                throw new RuntimeException("Failed to get response from Exaon API.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while calling Exaon API", e);
        }
    }

    @Override
    public ChatOptions getDefaultOptions() {
        return new ChatOptions() {
            @Override
            public String getModel() {
                return "exaon-default";
            }

            @Override
            public Double getFrequencyPenalty() {
                return null;
            }

            @Override
            public Integer getMaxTokens() {
                return 1000;
            }

            @Override
            public Double getPresencePenalty() {
                return null;
            }

            @Override
            public List<String> getStopSequences() {
                return null;
            }

            @Override
            public Double getTemperature() {
                return 0.7;
            }

            @Override
            public Integer getTopK() {
                return null;
            }

            @Override
            public Double getTopP() {
                return null;
            }

            @Override
            public ChatOptions copy() {
                return null;
            }
        };
    }
}
