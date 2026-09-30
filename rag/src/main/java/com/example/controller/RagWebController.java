package com.example.controller;

import com.example.service.DocumentIngestionService;
import com.example.service.RagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class RagWebController {

    private final DocumentIngestionService ingestionService;
    private final RagService ragService;

    public RagWebController(DocumentIngestionService ingestionService, RagService ragService) {
        this.ingestionService = ingestionService;
        this.ragService = ragService;
    }

    @GetMapping("/home")
    public String home() {
        return "index";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {

        try {
            int chunks = ingestionService.ingest(file);

            model.addAttribute(
                    "message",
                    file.getOriginalFilename()
                            + " indexed successfully. "
                            + chunks
                            + " chunks created.");

        } catch (Exception e) {
            model.addAttribute("error", "Failed to index document: " + e.getMessage());
        }

        return "index";
    }

    @PostMapping("/ask")
    public String ask(@RequestParam("question") String question, Model model) {

        String answer = ragService.chat(question);

        model.addAttribute("question", question);
        model.addAttribute("answer", answer);

        return "index";
    }
}
