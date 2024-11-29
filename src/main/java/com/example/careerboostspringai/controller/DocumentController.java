package com.example.careerboostspringai.controller;

import com.example.careerboostspringai.dto.Document;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final VectorStore vectorStore;

    @PostMapping(consumes = {"application/json", "text/csv"})
    @ResponseStatus(HttpStatus.CREATED)
    public void addDocument(
            @Valid @RequestBody List<Document> documents
    ) {
        vectorStore.add(documents.stream().map(Document::toSpringAiDocument).toList());
    }
}
