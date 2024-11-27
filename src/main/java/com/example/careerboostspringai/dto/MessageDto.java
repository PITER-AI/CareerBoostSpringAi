package com.example.careerboostspringai.dto;

import com.example.careerboostspringai.entity.ChatData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MessageDto {
    private String role;
    private String content;
    private List<String> ref;

    public MessageDto(String role, String content, List<String> ref) {
        this.role = role;
        this.content = content;
        this.ref = ref;
        //this.destination = destination;
    }

    public ChatData.Message toEntity() {
        return new ChatData.Message(role, content, ref);
    }

    public static MessageDto fromEntity(ChatData.Message message) {
        return new MessageDto(message.getRole(), message.getContent(), message.getRef());
    }
}