package com.example.kafka;

import com.example.Constants;
import com.example.KafkaUtil;

import org.apache.kafka.clients.consumer.*;

import java.time.Duration;
import java.util.Collections;

public class Consumer {

    public static void main(String[] args) {
        KafkaConsumer<String, String> consumer = KafkaUtil.getConsumer(Constants.BOOTSTRAP_SERVER_9092, Constants.CONSUMER_GROUP_ORDER_SERVICE, Constants.AUTO_OFFSET_EARLIEST);
        // Subscribe to the topic "order-events"
        consumer.subscribe(Collections.singletonList(Constants.TOPIC_ORDER_EVENTS));

        System.out.println("Consumer started...");

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf(
                        "Key=%s | Value=%s | Partition=%d | Offset=%d%n",
                        record.key(), record.value(), record.partition(), record.offset());
            }
        }
    }
}
