//package com.chamcongtinhluong.ai_service.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.jsoup.Jsoup;
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.ai.document.Document;
//import org.springframework.ai.transformer.splitter.TokenTextSplitter;
//import org.springframework.ai.vectorstore.SearchRequest;
//import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/chat")
//@RequiredArgsConstructor
//public class ChatController {
//    private final ChatClient chatClient;
//    private final QdrantVectorStore qdrantVectorStore;
//    String context = "";
//    @GetMapping
//    private String chatSystem(@RequestParam String message){
//        List<Document> documentList = qdrantVectorStore.similaritySearch(
//                SearchRequest.query(message).withTopK(10)
//        );
//
//        for (Document document: documentList){
//            context += document.getContent();
//        }
//
//        return chatClient.prompt().system(sp->sp.param("context",context))
//                .user(message).call().content();
////        return context;
//    }
//
//    @GetMapping("/knowlegde")
//    private void addFromURL(@RequestParam String url) throws IOException {
//        org.jsoup.nodes.Document jsoupDocuments;
//        Document document;
//        StringBuilder stringBuilder = new StringBuilder();
//
//        jsoupDocuments = Jsoup.connect(url).get();
//        stringBuilder.append(jsoupDocuments.body()
//                .text()).append("Nguon tham khao")
//                .append(jsoupDocuments.title()).append(". " +
//                        "Trang web (").append(url).append(" )\n");
//        document = new Document(stringBuilder.toString());
//
//        TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();
//        qdrantVectorStore.add(tokenTextSplitter.split(document));
//        System.out.println("Da luu");
//    }
//
//}
