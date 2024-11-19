package com.example.careerboostspringai.service;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

public class LpuService {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://0.0.0.0:8000:8000")
            .build();

    public Mono<String> generateText(String input) {
        return webClient.post()
                .uri("/api/v1/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"input\": \"" + input + "\"}")
                .retrieve()
                .bodyToMono(String.class);
    }
}
