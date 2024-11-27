package com.example.careerboostspringai.entity;

import jakarta.persistence.*;
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

        @ElementCollection
        @CollectionTable(name = "message_refs", joinColumns = @JoinColumn(name = "message_id"))
        @Column(name = "ref", columnDefinition = "TEXT")
        private List<String> ref;


        public Message(String role, String content, List<String> ref) {
            this.role = role;
            this.content = content;
            this.ref = ref;

        }
    }
}