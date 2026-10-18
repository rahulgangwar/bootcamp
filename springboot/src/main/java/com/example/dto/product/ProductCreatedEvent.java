package com.example.dto.product;

public record ProductCreatedEvent(Long productId, String name, Double price) {}
