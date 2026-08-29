package com.example.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Producer {

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        for (int i = 1; i <= 10; i++) {

            String orderId = "order-" + i;
            String event = "OrderCreated";

            ProducerRecord<String, String> record =
                    new ProducerRecord<>("order-events", orderId, event);

            producer.send(
                    record,
                    (metadata, exception) -> {
                        if (exception != null) {
                            exception.printStackTrace();
                            return;
                        }

                        System.out.println(
                                "Order="
                                        + record.key()
                                        + " | Partition="
                                        + metadata.partition()
                                        + " | Offset="
                                        + metadata.offset());
                    });
        }

        producer.flush();
        producer.close();
    }
}
