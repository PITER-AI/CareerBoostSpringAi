package com.example.careerboostspringai.controller;

import com.example.careerboostspringai.dto.ChatDataDto;
import com.example.careerboostspringai.dto.MessageDto;
import com.example.careerboostspringai.dto.RagChatDto;
import com.example.careerboostspringai.service.ChatDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class RagChatController {
    private final ChatClient exaonChatClient;
    private final ChatDataService chatDataService;

    @PostMapping
    public ResponseEntity<RagChatDto> chat(@RequestBody @Valid ChatDataDto chatDataDto) {
        String userMessage = chatDataDto.getMessages().get(chatDataDto.getMessages().size() - 1).getContent();

        var response = exaonChatClient.prompt()
                .user(userMessage)
                .call();

        String assistantMessage = response.getResult().getOutput().getContent();
        MessageDto assistantMsg = new MessageDto("assistant", assistantMessage);

        Long chatId = chatDataService.saveChatData(chatDataDto, assistantMsg);
        return ResponseEntity.ok(new RagChatDto(chatId, assistantMsg));
    }
}
