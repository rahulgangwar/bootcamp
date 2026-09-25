package com.example.kafka.event.producer;

import com.example.kafka.event.ProductEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ProductEventProducer {

    private static final String TOPIC = "product-events";

    private final KafkaTemplate<String, ProductEvent> kafkaTemplate;

    public ProductEventProducer(KafkaTemplate<String, ProductEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(ProductEvent event) {
        log.info("Publishing Kafka Event: {}", event);
        kafkaTemplate.send(TOPIC, event.productId().toString(), event);
    }
}
