package com.example.careerboostspringai.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    private final VectorStore vectorStore;

    public DocumentService(VectorStore vectorStore) {
        this.vectorStore = vectorStore; // 생성자 주입
    }

    public List<String> findSimilarDocuments(String userContent) {
        // 유사도 검색 요청 생성
        SearchRequest request = SearchRequest
                .query(userContent);

        // 유사한 문서 검색
        List<Document> similarDocuments = vectorStore.similaritySearch(request);

        // 문서에서 링크 추출
        return similarDocuments.stream()
                .map(doc -> (String) doc.getMetadata().get("link"))
                .collect(Collectors.toList());
    }

    public boolean isDocumentAvailable(String link) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(link).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                // 페이지 내용 확인
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                return !content.toString().contains("게시물에 접근할 수 없습니다"); // 해당 문구가 있을 경우 false 반환
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

}