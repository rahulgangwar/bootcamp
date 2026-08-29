# Kafka

Kafka CLI scripts are available under:

```text
/opt/kafka/bin
```

Enter the Kafka container:
```bash
docker exec -it kafka bash
cd /opt/kafka/bin
```

Important scripts to remember:

```bash
./kafka-topics.sh
./kafka-console-producer.sh
./kafka-console-consumer.sh
./kafka-consumer-groups.sh
./kafka-configs.sh
./kafka-metadata-quorum.sh
```

### 1. List All Topics

```bash
./kafka-topics.sh --list   --bootstrap-server localhost:9092
```

### 2. Describe a Topic

This is one of the most useful Kafka CLI commands.

```bash
./kafka-topics.sh --describe   --topic order-events   --bootstrap-server localhost:9092
```
Example output:

```text
Topic: order-events
PartitionCount: 3
ReplicationFactor: 1

Partition: 0
Leader: 1
Replicas: 1
Isr: 1

Partition: 1
Leader: 1
Replicas: 1
Isr: 1

Partition: 2
Leader: 1
Replicas: 1
Isr: 1
```

### 3. Create a Topic

```bash
./kafka-topics.sh --create   --topic payment-events   --bootstrap-server localhost:9092   --partitions 5   --replication-factor 1
```


### 4. Produce Messages

```bash
./kafka-console-producer.sh   --topic order-events   --bootstrap-server localhost:9092
```

Then enter:

```text
hello
order-created
order-paid
```

Each line represents a Kafka record.

Exit with:

```text
Ctrl+C
```

### 5. Consume Messages

```bash
./kafka-console-consumer.sh   --topic order-events   --bootstrap-server localhost:9092   --from-beginning
```


### 6. Inspect Partition and Offset

```bash
./kafka-console-consumer.sh   --topic order-events   --bootstrap-server localhost:9092   --from-beginning   --property print.partition=true   --property print.offset=true
```

### 7. Produce Messages With Keys

```bash
./kafka-console-producer.sh   --topic order-events   --bootstrap-server localhost:9092   --property parse.key=true   --property key.separator=:
```

Send:

```text
order-101:OrderCreated
order-102:OrderCreated
order-103:OrderCreated
order-101:OrderPaid
order-102:OrderPaid
```

The structure is:

```text
key:value
```

For example:

```text
order-101:OrderCreated
     │           │
     │           └── Value
     └────────────── Key
```

A key can influence which partition receives the record.

### 8. Consume Keys + Partitions + Offsets

```bash
./kafka-console-consumer.sh   --topic order-events   --bootstrap-server localhost:9092   --from-beginning   --property print.key=true   --property print.partition=true   --property print.offset=true
```

Look for events with the same key.

Example:

```text
Partition:1    Offset:5    order-101    OrderCreated
Partition:1    Offset:6    order-101    OrderPaid

Partition:2    Offset:3    order-102    OrderCreated
Partition:2    Offset:4    order-102    OrderPaid
```

Important pattern:

```text
Same key
   ↓
Same partition
   ↓
Ordered offsets within that partition
```

This is why business keys such as `orderId` are commonly used when events for the same entity must remain ordered.



### 9. Inspect Topic Configuration

```bash
./kafka-configs.sh   --bootstrap-server localhost:9092   --entity-type topics   --entity-name order-events   --describe
```

You may see configuration such as:

```text
retention.ms
segment.bytes
cleanup.policy
```

---

### 10. List Consumer Groups

```bash
./kafka-consumer-groups.sh   --bootstrap-server localhost:9092   --list
```

If the Java consumer has been run with:

```java
group.id = "order-service"
```

you should eventually see:

```text
order-service
```

### 11. Describe a Consumer Group ⭐⭐⭐

```bash
./kafka-consumer-groups.sh   --bootstrap-server localhost:9092   --describe   --group order-service
```

Example:

```text
TOPIC          PARTITION   CURRENT-OFFSET   LOG-END-OFFSET   LAG
order-events   0           10               10               0
order-events   1           8                8                0
order-events   2           12               12               0
```

Understand the three important fields:

### CURRENT-OFFSET

Where the consumer group has progressed.

### LOG-END-OFFSET

The latest available position in the partition.

### LAG

How far behind the consumer is.

Conceptually:

```text
Latest Kafka record
       ↑
       │
LOG-END-OFFSET
       │
       │  ← LAG
       │
CURRENT-OFFSET
       ↑
Consumer progress
```

Consumer lag becomes very important for production monitoring.

---

### 12. Inspect Internal Topics

Run:

```bash
./kafka-topics.sh --list   --bootstrap-server localhost:9092
```

Then:

```bash
./kafka-topics.sh --list   --bootstrap-server localhost:9092   --exclude-internal
```

Kafka has internal topics such as:

```text
__consumer_offsets
```

For now, just recognize that Kafka itself uses internal topics.

We will study `__consumer_offsets` when learning consumer offsets and commits.

---

### 13. Inspect the KRaft Metadata Quorum

The Kafka 4.x image uses KRaft.

Use:

```bash
./kafka-metadata-quorum.sh   --bootstrap-server localhost:9092   describe
```

For status:

```bash
./kafka-metadata-quorum.sh   --bootstrap-server localhost:9092   describe --status
```

Possible fields include:

```text
ClusterId
LeaderId
LeaderEpoch
HighWatermark
CurrentVoters
CurrentObservers
```

### 14. Delete a Topic

Example:

```bash
./kafka-topics.sh --delete   --topic payment-events   --bootstrap-server localhost:9092
```


# CLI Summary

The most useful commands are:

```text
kafka-topics.sh
    Create / list / describe topics

kafka-console-producer.sh
    Produce records

kafka-console-consumer.sh
    Consume records

kafka-consumer-groups.sh
    Inspect consumer groups, offsets and lag

kafka-configs.sh
    Inspect configuration

kafka-metadata-quorum.sh
    Inspect KRaft metadata quorum
```

For this local environment, remember the invocation convention:

```bash
cd /opt/kafka/bin
./kafka-<command>.sh
```