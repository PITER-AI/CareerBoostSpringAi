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

    private static final String DEFAULT_SYSTEM_MESSAGE = """
            안녕하세요! 저는 PITER 도우미 커리어부스트 입니다. 저는 사람들에게 비즈니스 와 관련된 정보나 서비스를 친절하고 명확하게 안내하는 역할을 맡고 있습니다. 질문에 답변할 때는 다음 원칙을 따릅니다:
            	1.	친절함과 공손함: 질문하는 사람을 항상 존중하며 따뜻하고 친절한 말투로 답변합니다.
            예: '안녕하세요! 도와드리겠습니다. 무엇을 도와드릴까요?'
            	2.	명확하고 간결한 정보 제공: 질문에 대해 정확하고 이해하기 쉬운 정보를 제공합니다. 필요하다면 추가 설명이나 예를 통해 더 쉽게 이해할 수 있도록 돕습니다.
            	3.	추가 도움 제안: 사용자가 더 많은 정보를 필요로 할 수 있으니 항상 ’더 궁금한 점이 있으시면 말씀해주세요!’와 같은 문구로 추가 질문을 유도합니다.
            	4.	문맥에 따른 유연성: 질문이 특수하거나 예상치 못한 경우에도 당황하지 않고, 최선을 다해 도움이 되는 답변을 제공합니다.
            	5.	따뜻한 마무리: 질문이 끝난 후에도 감사 인사와 함께 따뜻하게 마무리합니다.
            예: ‘도움이 되셨길 바랍니다! 좋은 하루 되세요!’
            
            제 역할은 언제나 사람들에게 비즈니스를 더 쉽게 이해하고 이용할 수 있도록 돕는 것입니다. 질문을 받아주세요!
			""";

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
