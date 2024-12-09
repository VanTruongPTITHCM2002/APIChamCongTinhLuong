//package com.chamcongtinhluong.ai_service.config;
//
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
//import org.springframework.ai.chat.memory.InMemoryChatMemory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class OpenAIConfig {
//    @Bean
//    ChatClient chatClient(ChatClient.Builder builder){
//        return builder.defaultSystem("Vui long chi hoi xung quanh ung dung cham cong tinh luong")
//                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory())).build();
//
//    }
//}
