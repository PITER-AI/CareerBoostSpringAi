package com.example.careerboostspringai.service;

import com.example.careerboostspringai.dto.LpuResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//@Slf4j
//@Service
//public class LpuService {
//    private final WebClient webClient;
//
//    public LpuService() {
//        this.webClient = WebClient.builder()
//                .baseUrl("http://localhost:8000")
//                .build();
//    }
//
//    public Mono<String> generateText(String input) {
//        Map<String, Object> request = new HashMap<>();
//        request.put("inputs", input);
//        request.put("parameters", Map.of(
//                "max_new_tokens", 256,
//                "temperature", 0.7,
//                "stream", false
//        ));
//
//        log.debug("Sending request to LPU server: {}", request);
//
//        return webClient.post()
//                .uri("/generate")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(request)
//                .retrieve()
//                .onStatus(HttpStatusCode::is5xxServerError,
//                        clientResponse -> {
//                            log.error("Server error status: {}", clientResponse.statusCode());
//                            return Mono.error(new RuntimeException("Server error: " + clientResponse.statusCode()));
//                        })
//                .bodyToMono(String.class)
//                .doOnError(error -> log.error("Error calling LPU server", error))
//                .doOnSuccess(response -> log.debug("LPU server response: {}", response));
//    }
//}

@Slf4j
@Service
public class LpuService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;  // Jackson ObjectMapper 주입

    public LpuService(ObjectMapper objectMapper) {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8000")
                .build();
        this.objectMapper = objectMapper;
    }

    public Mono<String> generateText(String input) {
        Map<String, Object> request = new HashMap<>();
        request.put("inputs", input);
        request.put("parameters", Map.of(
                "max_new_tokens", 256,
                "temperature", 0.7,
                "stream", false
        ));

        return webClient.post()
                .uri("/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        // 이중 JSON 파싱
                        JsonNode responseNode = objectMapper.readTree(response);
                        if (responseNode.isArray() && !responseNode.isEmpty()) {
                            return responseNode.get(0)
                                    .get("generated_text")
                                    .asText()
                                    .replaceAll("\\\\n", "\n")  // 이스케이프된 newline 처리
                                    .replaceAll("\\\\\"", "\"");
                        }
                        return response;
                    } catch (Exception e) {
                        log.error("Error parsing response", e);
                        return response;
                    }
                });
    }
}