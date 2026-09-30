package com.example.controller;

import com.example.service.RagService;

import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rag")
public class RagController {

    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/search")
    public List<Document> search(@RequestParam String question) {
        return ragService.search(question);
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String question) {
        return ragService.chat(question);
    }
}
