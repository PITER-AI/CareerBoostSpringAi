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

    private static final String DEFAULT_SYSTEM_MESSAGE = "You are EXAONE model from LG AI Research, a helpful assistant";

//    @Bean
//    public ChatClient exaonChatClient(ChatModel exaonChatModel) {
//        return ChatClient.builder(exaonChatModel).build();
//    }

//    @Primary
//    @Bean
//    public ChatModel lgChatModel() {
//        return new LGChatModel();
//    }

    @Bean
    @Primary
    ChatClient.Builder Builder(LGChatModel lgChatModel) {
        return ChatClient.builder(lgChatModel);
    }

    @Bean
    ChatClient ragChatClient(ChatClient.Builder builder, QuestionAnswerAdvisor questionAnswerAdvisor) {
        return builder
                .clone()
                .defaultSystem(DEFAULT_SYSTEM_MESSAGE)
                .defaultAdvisors(questionAnswerAdvisor)
                .build();
    }


//    @Bean
//    ChatClient LGChatClient(ChatModel lgChatModel, QuestionAnswerAdvisor questionAnswerAdvisor) {
//        return ChatClient.builder(lgChatModel)
//                .defaultSystem(DEFAULT_SYSTEM_MESSAGE)
//                .defaultAdvisors(questionAnswerAdvisor)
//                .build();
//    }
}
