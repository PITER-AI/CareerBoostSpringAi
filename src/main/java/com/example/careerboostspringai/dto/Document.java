package com.example.careerboostspringai.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Document {

    @NotBlank
    private String input;

    @NotBlank
    private String output;

    public Map<String, Object> toSpringMetadata() {
        return Map.of(
                "input", this.input,
                "output", this.output
        );
    }

    public org.springframework.ai.document.Document toSpringAiDocument() {
        return new org.springframework.ai.document.Document(this.input, this.toSpringMetadata());
    }
}