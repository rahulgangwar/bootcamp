
### Connecting to Kafka Container
```bash
docker exec -it kafka bash
cd /opt/kafka/bin
```

### List Kafka Topics
```bash
./kafka-topics.sh \
--bootstrap-server kafka:9092 \
--list
```