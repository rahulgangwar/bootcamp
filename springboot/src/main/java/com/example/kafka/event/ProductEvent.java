package com.example.kafka.event;

import java.math.BigDecimal;

public record ProductEvent(
        String eventType,
        Long productId,
        String name,
        BigDecimal price
) {
}