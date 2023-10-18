package com.pengyu.magnet.langchain4j;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Langchain4JConfig {
    @Bean
    AssessmentAgent assessmentAgent(ChatLanguageModel chatLanguageModel) {
        return AiServices.builder(AssessmentAgent.class)
                .chatLanguageModel(chatLanguageModel)
//                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .build();
    }

    @Bean
    MatchAgent matchAgent(ChatLanguageModel chatLanguageModel) {
        return AiServices.builder(MatchAgent.class)
                .chatLanguageModel(chatLanguageModel)
//                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .build();
    }

}
