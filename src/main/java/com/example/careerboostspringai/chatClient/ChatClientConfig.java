package com.example.careerboostspringai.chatClient;

import com.example.careerboostspringai.LGChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ChatClientConfig {
    @Bean
    @Primary
    public ChatClient lgChatClient(LGChatModel lgChatModel, QuestionAnswerAdvisor questionAnswerAdvisor) {
        return ChatClient.builder(lgChatModel)
                .defaultAdvisors(questionAnswerAdvisor)
                .build();
    }
}
