package com.example.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RagService {

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public RagService(VectorStore vectorStore, ChatClient.Builder chatClientBuilder) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClientBuilder.build();
    }

    public List<Document> search(String question) {
        SearchRequest request = SearchRequest.builder().query(question).topK(3).build();
        return vectorStore.similaritySearch(request);
    }

    public String chat(String question) {
        List<Document> documents = search(question);
        String context =
                documents.stream().map(Document::getText).collect(Collectors.joining("\n\n"));
        return chatClient
                .prompt()
                .system(
                        """
                        You are a helpful assistant.
                        Answer the user's question using only the provided context.
                        If the answer is not present in the context, say you don't know.
                        """)
                .user(
                        """
                        Context:
                        %s

                        Question:
                        %s
                        """
                                .formatted(context, question))
                .call()
                .content();
    }
}
