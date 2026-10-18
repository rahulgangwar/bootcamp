package com.example.kafka.event.producer;

import com.example.dto.product.ProductCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductEventProducer {

    private static final String TOPIC = "product-events";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public ProductEventProducer(
            KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(ProductCreatedEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);

            kafkaTemplate.send(TOPIC, event.productId().toString(), payload);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize product event", e);
        }
    }
}
