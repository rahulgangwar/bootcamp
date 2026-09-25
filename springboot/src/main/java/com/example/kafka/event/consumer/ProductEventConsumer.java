package com.example.kafka.event.consumer;

import com.example.kafka.event.ProductEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ProductEventConsumer {

    @KafkaListener(topics = "product-events", groupId = "product-cache-consumer")
    public void consume(ProductEvent event) {
        log.info("Kafka Event Consumed: {}", event);
    }
}
