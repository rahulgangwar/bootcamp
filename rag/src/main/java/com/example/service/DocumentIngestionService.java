package com.example.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class DocumentIngestionService {

    private final VectorStore vectorStore;

    public DocumentIngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public int ingest(MultipartFile file) throws IOException {
        String text = new String(file.getBytes(), StandardCharsets.UTF_8);
        Document document = new Document(text, Map.of("source", file.getOriginalFilename()));

        TokenTextSplitter splitter =
                TokenTextSplitter.builder()
                        .withChunkSize(50)
                        .withMinChunkSizeChars(20)
                        .withMinChunkLengthToEmbed(5)
                        .withMaxNumChunks(100)
                        .withKeepSeparator(true)
                        .build();

        List<Document> chunks = splitter.apply(List.of(document));
        vectorStore.add(chunks);
        return chunks.size();
    }
}
