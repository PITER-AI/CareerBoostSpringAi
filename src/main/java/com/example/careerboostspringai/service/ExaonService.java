package com.example.careerboostspringai.service;

import com.example.careerboostspringai.dto.ExaonOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExaonService {
    private final ChatClient exaonChatClient;

    public ExaonOutput getExaonOutput(String userMessage) {
        // 엑사온 모델에 사용자 메시지 전달 및 응답 수신
        var response = exaonChatClient.prompt()
                .user(userMessage)
                .call();

        // 응답 내용 추출
        String content = response.content();
        List<String> refLists = extractReferences(content);

        return new ExaonOutput(content, refLists);
    }

    private List<String> extractReferences(String content) {
        // 응답 내용에서 참조 정보를 추출하는 로직을 구현합니다.
        return new ArrayList<>();
    }
}

//@Service
//@RequiredArgsConstructor
//public class ExaonService {
//    private final ChatClient exaonChatClient;
//
//    public ExaonOutput getExaonOutput(String userMessage) {
//        // 사용자 메시지와 파라미터를 포함한 요청 생성
//        var response = exaonChatClient.prompt()
//                .rawRequest(createRequest(userMessage))
//                .call();
//
//        // 응답에서 데이터 추출
//        String content = response.content();
//        List<String> refLists = extractReferences(content);
//
//        return new ExaonOutput(content, refLists);
//    }
//
//    private Map<String, Object> createRequest(String userMessage) {
//        // 요청 데이터를 생성합니다.
//        Map<String, Object> request = new HashMap<>();
//        request.put("inputs", List.of(userMessage));
//        request.put("parameters", Map.of(
//                "temperature", 0.7,
//                "top_p", 0.95,
//                "max_new_tokens", 256
//        ));
//        return request;
//    }
//
//    private List<String> extractReferences(String content) {
//        // 응답 내용에서 참조 정보를 추출합니다.
//        return new ArrayList<>();
//    }
//}


