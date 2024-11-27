package com.example.careerboostspringai.chatClient;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient exaonChatClient(ChatModel exaonChatModel) {
        return ChatClient.builder(exaonChatModel).build();
    }
}
