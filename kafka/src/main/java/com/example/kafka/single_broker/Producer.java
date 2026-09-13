package com.example.kafka.single_broker;

import com.example.Constants;
import com.example.KafkaUtil;

import org.apache.kafka.clients.producer.*;

public class Producer {

    private static KafkaProducer<String, String> producer =
            KafkaUtil.getProducer(Constants.BOOTSTRAP_SERVER_9092);
    private static String[] events = {
        "ORDER_CREATED",
        "PAYMENT_INITIATED",
        "PAYMENT_COMPLETED",
        "ORDER_CONFIRMED",
        "ORDER_SHIPPED"
    };

    public static void main(String[] args) {
        producer = KafkaUtil.getProducer(Constants.BOOTSTRAP_SERVER_9092);

        try {
            while (true) {
                String orderId = "order-" + System.currentTimeMillis();
                publishEvent(orderId, events);

                // Optional: slow down production
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            producer.flush();
            producer.close();
            System.out.println("Producer stopped.");
        }
    }

    private static void publishEvent(String orderId, String[] events) {
        for (String event : events) {
            ProducerRecord<String, String> record =
                    new ProducerRecord<>(Constants.TOPIC_ORDER_EVENTS, orderId, event);

            producer.send(
                    record,
                    (metadata, exception) -> {
                        if (exception != null) {
                            exception.printStackTrace();
                            return;
                        }

                        System.out.printf(
                                "Published | Order=%s | Event=%s | Partition=%d | Offset=%d%n",
                                record.key(),
                                record.value(),
                                metadata.partition(),
                                metadata.offset());
                    });
        }
    }
}
