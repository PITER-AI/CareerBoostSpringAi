package com.example.careerboostspringai.dto;

import com.example.careerboostspringai.entity.ChatData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class MessageDto {
    private String role;
    private String content;


    public MessageDto(String role, String content) {
        this.role = role;
        this.content = content;

    }

    public ChatData.Message toEntity() {
        return new ChatData.Message(role, content);
    }

    public static MessageDto fromEntity(ChatData.Message message) {
        return new MessageDto(message.getRole(), message.getContent());
    }
}