package com.example.kafka.event.consumer;

import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ProductCDCConsumer {

    @KafkaListener(
            topics = "mysql.main_db.products",
            groupId = "product-cdc-consumer",
            containerFactory = "cdcKafkaListenerContainerFactory")
    public void consume(String message) {
        log.info("CDC EVENT RECEIVED: {}", message);
    }
}
