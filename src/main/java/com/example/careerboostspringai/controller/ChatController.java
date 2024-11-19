package com.example.careerboostspringai.controller;

import com.example.careerboostspringai.service.LpuService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

//import static org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor.RETRIEVED_DOCUMENTS;

@RestController
public class ChatController {

    @Autowired
    private LpuService lpuService;

    @PostMapping("/chat")
    public Mono<String> chat(@RequestBody String userInput) {
        return lpuService.generateText(userInput);
    }
}