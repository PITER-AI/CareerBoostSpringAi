package com.example.careerboostspringai.controller;

import com.example.careerboostspringai.dto.ChatRequest;
import com.example.careerboostspringai.service.LpuService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//import static org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor.RETRIEVED_DOCUMENTS;

//@RestController
//public class ChatController {
//
//    private final LpuService lpuService;
//
//    @Autowired
//    public ChatController(LpuService lpuService) {
//        this.lpuService = lpuService;
//    }
//
//    @PostMapping("/chat")
//    public Mono<Map<String, String>> chat(@RequestBody ChatRequest chatRequest) {
//        return lpuService.generateText(chatRequest.getMessage())
//                .map(response -> {
//                    Map<String, String> result = new HashMap<>();
//                    result.put("response", response);
//                    return result;
//                });
//    }
//}

@RestController
public class ChatController {
    private final LpuService lpuService;

    public ChatController(LpuService lpuService) {
        this.lpuService = lpuService;
    }

    @PostMapping("/chat")
    public Mono<Map<String, String>> chat(@RequestBody ChatRequest chatRequest) {
        return lpuService.generateText(chatRequest.getMessage())
                .map(response -> Collections.singletonMap("response", response));
    }
}