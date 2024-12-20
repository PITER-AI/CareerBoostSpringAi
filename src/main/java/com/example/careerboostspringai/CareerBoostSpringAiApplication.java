package com.example.careerboostspringai;

import com.example.careerboostspringai.vector.PineconeVectorStore;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CareerBoostSpringAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CareerBoostSpringAiApplication.class, args);
	}


	@Bean
	QuestionAnswerAdvisor questionAnswerAdvisor(VectorStore vectorStore) {
		return new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults(), "{question_answer_context} ") ;
	}

	@Bean
	VectorStore vectorStore(PineconeVectorStore.PineconeVectorStoreConfig pineconeVectorStoreConfig, EmbeddingModel embeddingModel) {
		return new PineconeVectorStore(pineconeVectorStoreConfig, embeddingModel);
	}

	@Bean
	PineconeVectorStore.PineconeVectorStoreConfig pineconeVectorStoreConfig(
			@Value("${spring.ai.vectorstore.pinecone.api-key}") String apiKey,
			@Value("${spring.ai.vectorstore.pinecone.index-name}") String indexName,
			@Value("${spring.ai.vectorstore.pinecone.namespace}") String namespace
	) {
		var builder = PineconeVectorStore.PineconeVectorStoreConfig.builder()
				.withApiKey(apiKey)
				.withIndexName(indexName)
				.withNamespace(namespace);

		return new PineconeVectorStore.PineconeVectorStoreConfig(builder);
	}
}
