package com.example.careerboostspringai.controller;

import com.example.careerboostspringai.dto.*;
import com.example.careerboostspringai.service.ChatDataService;
import com.example.careerboostspringai.service.DocumentService;
import com.example.careerboostspringai.service.ExaonService;
import com.example.careerboostspringai.service.RagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController
//@RequestMapping("/api/v1/chat")
//@RequiredArgsConstructor
//public class RagChatController {
//    private final ChatClient exaonChatClient;
//    private final ChatDataService chatDataService;
//
//    @PostMapping
//    public ResponseEntity<RagChatDto> chat(@RequestBody @Valid ChatDataDto chatDataDto) {
//
//        List<MessageDto> messages = chatDataDto.getMessages();
//
//
//        String userMessage = chatDataDto.getMessages().get(chatDataDto.getMessages().size() - 1).getContent();
//
//        var response = exaonChatClient.prompt()
//                .user(userMessage)
//                .call();
//
//        String assistantMessage = response.getResult().getOutput().getContent();
//        MessageDto assistantMsg = new MessageDto("assistant", assistantMessage, null);
//
//        Long chatId = chatDataService.saveChatData(chatDataDto, assistantMsg);
//        return ResponseEntity.ok(new RagChatDto(chatId, assistantMsg));
//    }
//}

//@RestController
//@RequestMapping("/api/v1/chat")
//@RequiredArgsConstructor
//public class RagChatController {
//    private final ChatDataService chatDataService;
//    private final ExaonService exaonService;
//
//    @PostMapping
//    public ResponseEntity<RagChatDto> chat(@RequestBody @Valid ChatDataDto chatDataDto) {
//        List<MessageDto> messages = chatDataDto.getMessages();
//        MessageDto lastMessage = messages.get(messages.size() - 1);
//        String query = lastMessage.getContent();
//
//        // 엑사온 모델을 통해 응답 생성
//        ExaonOutput exaonOutput = exaonService.getExaonOutput(query);
//        String content = exaonOutput.getContent();
//        List<String> refLists = exaonOutput.getRefLists();
//
//        // 어시스턴트 메시지 생성
//        MessageDto assistantMsg = new MessageDto("assistant", content, refLists);
//
//        // 채팅 데이터 저장 또는 업데이트
//        Long chatID = chatDataDto.getChatId();
//        if (chatID == null) {
//            chatID = chatDataService.saveChatData(chatDataDto, assistantMsg);
//        } else {
//            chatDataService.addMessage(chatID, lastMessage, assistantMsg);
//        }
//
//        // 응답 DTO 생성
//        RagChatDto ragChatDto = new RagChatDto(chatID, assistantMsg);
//        return ResponseEntity.ok(ragChatDto);
//    }
//}


//@RestController
//@RequestMapping("/api/v1/chat")
//@RequiredArgsConstructor
//public class RagChatController {
//    private final ExaonService exaonService;
//    private ChatDataService chatDataService;

//    @PostMapping
//    public ResponseEntity<RagChatDto> chat(@RequestBody @Valid ChatDataDto chatDataDto) {
//        if (chatDataDto.getMessages() == null || chatDataDto.getMessages().isEmpty()) {
//            throw new IllegalArgumentException("Messages field is required and cannot be empty.");
//        }
//
//        // 마지막 메시지를 가져옴
//        String userMessage = chatDataDto.getMessages()
//                .get(chatDataDto.getMessages().size() - 1)
//                .getContent();
//
//        // 엑사온 모델 호출
//        ExaonOutput exaonOutput = exaonService.getExaonOutput(userMessage);
//
//        // 어시스턴트 메시지 생성
//        MessageDto assistantMsg = new MessageDto("assistant", exaonOutput.getContent(), exaonOutput.getRefLists());
//        RagChatDto response = new RagChatDto(null, assistantMsg);
//
//        return ResponseEntity.ok(response);
//    }

//    @PostMapping
//    public ResponseEntity<RagChatDto> chat(@RequestBody @Valid ChatDataDto chatDataDto) {
//        List<MessageDto> messages = chatDataDto.getMessages();
//
//        // messages 필드 검증
//        if (messages == null || messages.isEmpty()) {
//            throw new IllegalArgumentException("Messages field is required and cannot be empty.");
//        }
//
//        // 마지막 메시지 가져오기
//        MessageDto lastMessage = messages.get(messages.size() - 1);
//        String userMessage = lastMessage.getContent();
//
//        // 엑사온 API 호출
//        var response = exaonService.getExaonOutput(userMessage);
//
//        // 응답 생성
//        MessageDto assistantMessage = new MessageDto("assistant", response.getContent(), response.getRefLists());
//        Long chatId = chatDataService.saveChatData(chatDataDto, assistantMessage);
//
//        RagChatDto ragChatDto = new RagChatDto(chatId, assistantMessage);
//        return ResponseEntity.ok(ragChatDto);
//    }
//}

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController
//@RequestMapping("/api/v1/chat")
//@RequiredArgsConstructor
//public class RagChatController {
//    private final ChatDataService chatDataService;
//    private final RagService ragService;
//    private final DocumentService documentService;
//
//    @PostMapping
//    public ResponseEntity<RagChatDto> chat(@RequestBody @Valid ChatDataDto chatDataDto) {
//
//        List<MessageDto> messages = chatDataDto.getMessages();
//
//        // 마지막 메시지의 content 가져오기
//        MessageDto lastMessage = messages.get(messages.size() - 1);
//        String query = lastMessage.getContent();
//
//
//        String content = "";
//        List<String> refLists = null;
//
//        var ragOutPut = ragService.getRagOutPut(chatDataDto);
//        content = ragOutPut.content();
//        refLists = ragOutPut.refLists();
//
//
//
//        MessageDto assistantMsg = new MessageDto("assistant", content, refLists);
//
//        Long chatID = chatDataDto.getChatId();
//        if (chatDataDto.getChatId() == null) {
//            chatID = chatDataService.saveChatData(chatDataDto, assistantMsg);
//        }
//        else {
//            chatDataService.addMessage(chatID, lastMessage, assistantMsg);
//        }
//
//        RagChatDto ragChatDto = new RagChatDto(chatID, assistantMsg);
//
//
//        return ResponseEntity.ok(ragChatDto);
//    }
//
//}

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class RagChatController {
    private final ChatDataService chatDataService;
    private final RagService ragService;
    private final DocumentService documentService;

    @PostMapping
    public ResponseEntity<RagChatResponse> chat(@RequestBody @Valid ChatDataDto chatDataDto) {
        try {
            List<MessageDto> messages = chatDataDto.getMessages();
            if (messages == null || messages.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            // 마지막 메시지의 content 가져오기
            MessageDto lastMessage = messages.get(messages.size() - 1);
            String query = lastMessage.getContent();

            // RAG 서비스 호출
            var ragOutput = ragService.getRagOutPut(chatDataDto);
            String content = ragOutput.content();
            //List<String> refLists = ragOutput.refLists();

            // 새로운 메시지 생성
            MessageDto assistantMsg = new MessageDto("assistant", content);

            // Chat ID 처리
            Long chatId = chatDataDto.getChatId();
            if (chatId == null) {
                chatId = chatDataService.saveChatData(chatDataDto, assistantMsg);
            } else {
                chatDataService.addMessage(chatId, lastMessage, assistantMsg);
            }

            // 응답 객체 생성
            RagChatResponse response = RagChatResponse.builder()
                    .chatId(chatId)
                    .message(assistantMsg)
                    .status("success")
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // 에러 응답 생성
            RagChatResponse errorResponse = RagChatResponse.builder()
                    .status("error")
                    .error(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }
}