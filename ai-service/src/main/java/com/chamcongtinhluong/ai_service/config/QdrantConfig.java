//package com.chamcongtinhluong.ai_service.config;
//
//import io.qdrant.client.QdrantClient;
//import io.qdrant.client.QdrantGrpcClient;
//import org.springframework.ai.embedding.EmbeddingModel;
//import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class QdrantConfig {
//    @Bean
//    public QdrantClient qdrantClient(){
//        QdrantGrpcClient.Builder grpClientBuilder = QdrantGrpcClient
//                .newBuilder("666a6bd0-068b-45ed-a4d4-9919a1c89b5d.us-east4-0.gcp.cloud.qdrant.io",
//                        6334,true);
//        grpClientBuilder.withApiKey("HrvXRLQz21-FAfqg0n5Q8Ax2rLihasTKsF7RQFDOqmk7pYarVQbfxQ");
//        return new QdrantClient(grpClientBuilder.build());
//    }
//
//    @Bean
//    public QdrantVectorStore vectorStore(EmbeddingModel embeddingModel,QdrantClient qdrantClient){
//        return new QdrantVectorStore(qdrantClient,"vantruong",embeddingModel,true);
//    }

//    @Bean
//    public EmbeddingModel embeddingModel(@Value("${huggingface.api.key}") String apiKey) {
//        return new HuggingFaceEmbeddingModel(apiKey, "bert-base-nli-mean-tokens"); // Ví dụ về mô hình của Hugging Face
//    }
//}
