package com.example.controller;

import com.example.service.DocumentIngestionService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/documents")
public class IngestionController {

    private final DocumentIngestionService ingestionService;

    public IngestionController(DocumentIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

//    @PostMapping("/ingest")
//    public String ingest() {
//        ingestionService.ingest();
//        return "Document ingested successfully";
//    }
}
