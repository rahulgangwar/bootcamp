package com.example.kafka.single_broker;

import com.example.Constants;
import com.example.KafkaUtil;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class Consumer {

    public static void main(String[] args) {
        KafkaConsumer<String, String> consumer =
                KafkaUtil.getConsumer(
                        Constants.BOOTSTRAP_SERVER_9092,
                        Constants.CONSUMER_GROUP_ORDER_SERVICE,
                        Constants.AUTO_OFFSET_EARLIEST);

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

            // Commit offsets after processing
            consumer.commitAsync();
            printPartitionInfo(consumer);
        }
    }

    private static void printPartitionInfo(KafkaConsumer<String, String> consumer) {
        Set<TopicPartition> partitions = consumer.assignment();
        Map<TopicPartition, OffsetAndMetadata> committed = consumer.committed(partitions);

        for (TopicPartition partition : partitions) {
            long position = consumer.position(partition);
            OffsetAndMetadata committedOffset = committed.get(partition);
            System.out.printf(
                    "Partition=%d | Position=%d | Committed=%s%n",
                    partition.partition(),
                    position,
                    committedOffset == null ? "null" : committedOffset.offset());
        }
    }
}
