
# Kafka
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
# Kafka Connect
### Creating a MySQL Connector in Kafka Connect
```bash
curl -X POST \
http://localhost:8083/connectors \
-H "Content-Type: application/json" \
-d @mysql-connector.json
```

### Check connector plugins in Kafka Connect
```bash
curl http://localhost:8083/connector-plugins
```


### Check connector status in Kafka Connect
```bash
curl http://localhost:8083/connectors/mysql-cdc-connector/status
```
