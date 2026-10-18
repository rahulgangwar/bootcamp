package com.example.kafka.event;


public record ProductEvent(String eventType, Long productId, String name, Double price) {}
