package com.example.careerboostspringai.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "chat_data_id")
    private List<Message> messages;

    @Getter
    @Setter
    @Entity
    @NoArgsConstructor
    public static class Message {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String role;

        @Column(columnDefinition = "CLOB")
        private String content;



        public Message(String role, String content) {
            this.role = role;
            this.content = content;


        }
    }
}