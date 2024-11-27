package com.example.careerboostspringai.dto;

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
    private String destination;

    public MessageDto(String role, String content, List<String> ref, String destination) {
        this.role = role;
        this.content = content;
        this.ref = ref;
        this.destination = destination;
    }

    public ChatData.Message toEntity() {
        return new ChatData.Message(role, content, ref, destination);
    }

    public static MessageDto fromEntity(ChatData.Message message) {
        return new MessageDto(message.getRole(), message.getContent(), message.getRef(), message.getDestination());
    }
}