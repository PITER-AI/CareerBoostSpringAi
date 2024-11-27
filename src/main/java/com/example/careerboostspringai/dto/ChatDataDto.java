package com.example.careerboostspringai.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatDataDto {
    private Long chatId;
    private String who = "Unknown";
    private String major = "Unknown";
    private boolean openAi = true;
    @NotNull @NotEmpty
    private List<MessageDto> messages;
}