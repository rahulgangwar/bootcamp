


### Check logs in logstash
```bash
docker logs -f logstash
```


# ELK Logging POC

This POC demonstrates centralized logging for a Spring Boot application using:

- **Spring Boot** — generates application logs
- **Logback** — formats and sends logs
- **Logstash** — receives and processes logs
- **Elasticsearch** — stores and indexes logs
- **Kibana** — searches and visualizes logs

## Architecture

```text
                    YOUR MACHINE
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│   Spring Boot Application                                   │
│                                                             │
│   ControllerLoggingAspect                                   │
│          │                                                  │
│          │ log.info(), log.error(), etc.                    │
│          ▼                                                  │
│   Logback                                                   │
│          │                                                  │
│          │ JSON over TCP :5000                              │
│          ▼                                                  │
│   ┌─────────────────┐                                       │
│   │    Logstash     │                                       │
│   │                 │                                       │
│   │ Input           │                                       │
│   │ Filter          │                                       │
│   │ Output          │                                       │
│   └────────┬────────┘                                       │
│            │                                                │
│            │ HTTP                                           │
│            ▼                                                │
│   ┌─────────────────────┐                                   │
│   │   Elasticsearch      │                                  │
│   │                     │                                   │
│   │ springboot-logs-*   │                                   │
│   └──────────┬──────────┘                                   │
│              │                                              │
│              │ Query                                        │
│              ▼                                              │
│   ┌─────────────────────┐                                   │
│   │       Kibana        │                                   │
│   │                     │                                   │
│   │ Discover            │                                   │
│   │ Dashboards          │                                   │
│   │ Visualizations      │                                   │
│   └─────────────────────┘                                   │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

## End-to-End Flow

```text
Spring Boot
     │
     │ Application logs
     ▼
   Logback
     │
     │ JSON over TCP :5000
     ▼
  Logstash
     │
     │ Process / Transform
     ▼
Elasticsearch
     │
     │ Search / Query
     ▼
   Kibana
```

## 1. Spring Boot Application

The Spring Boot application generates logs using SLF4J/Logback.

Example:

```java
private static final Logger log = LoggerFactory.getLogger(ProductService.class);

log.info("Product created successfully");
log.info("Fetching product {}", productId);
log.warn("Product stock is low for {}", productId);
log.error("Failed to create product {}", productId);
```

Our application also has request/response logging:

```text
2026-10-02 13:08:57.539 INFO [http-nio-8080-exec-1] c.e.aspect.ControllerLoggingAspect - [RESPONSE] [REQ-1790926737531-54] Status: 200 OK, Type: PageImpl
```

The `requestId` is particularly useful because it allows us to trace a request through multiple services.

Example:

```text
REQ-1790926737531-54
```

## 2. Logback

Logback is the logging framework used by Spring Boot.

Instead of only writing logs to the console, we configure Logback to send logs to Logstash.

```text
Spring Boot
     │
     ├──────────────> Console
     │
     └──────────────> Logstash :5000
```

We use the Logstash Logback Encoder to generate structured JSON logs.

Example:

```json
{
  "@timestamp": "2026-10-02T13:08:57.539Z",
  "level": "INFO",
  "message": "[RESPONSE] [REQ-1790926737531-54] Status: 200 OK, Type: PageImpl",
  "logger_name": "c.e.aspect.ControllerLoggingAspect",
  "thread_name": "http-nio-8080-exec-1"
}
```

## 3. Logstash

Logstash receives the JSON logs from the Spring Boot application.

Our Logstash input:

```text
input {
  tcp {
    port => 5000
    codec => json
  }
}
```

The flow is:

```text
Spring Boot
     │
     │ TCP :5000
     ▼
 Logstash
```

Logstash has three main responsibilities:

```text
Input
  ↓
Filter
  ↓
Output
```

### Input

Receives logs:

```text
TCP :5000
```

### Filter

Currently our filter is empty:

```text
filter {
}
```

Later we can use it to:

- Extract request ID
- Extract HTTP status
- Extract endpoint
- Extract response time
- Add service name
- Add environment
- Parse application-specific fields

### Output

Logs are sent to Elasticsearch:

```text
output {
  elasticsearch {
    hosts => ["http://elasticsearch:9200"]
    index => "springboot-logs-%{+YYYY.MM.dd}"
  }
}
```

## 4. Elasticsearch

Elasticsearch is responsible for storing and indexing the logs.

Our logs are stored in indexes such as:

```text
springboot-logs-2026.10.02
springboot-logs-2026.10.03
springboot-logs-2026.10.04
```

Elasticsearch allows us to perform fast searches across a large number of log events.

For example:

```text
"REQ-1790926737531-54"
```

or:

```text
message: "Status: 200 OK"
```

or:

```text
message: "ERROR"
```

## 5. Kibana

Kibana is the UI layer on top of Elasticsearch.

Kibana does not primarily store the logs.

Instead:

```text
             Kibana
                │
                │ Query
                ▼
         Elasticsearch
                │
                │ Results
                ▼
             Kibana
                │
                ▼
           User / Developer
```

Kibana provides:

- Log searching
- Filtering
- Discover
- Dashboards
- Visualizations
- Monitoring

## 6. Searching Logs

In Kibana:

```text
Analytics → Discover
```

Select the data view:

```text
springboot-logs-*
```

### Search by request ID

To find all logs containing a specific request ID:

```text
"REQ-1790926737531-54"
```

Or, if the value is stored in the `message` field:

```text
message: "REQ-1790926737531-54"
```

This can return:

```text
[REQUEST] [REQ-1790926737531-54] GET /products

[RESPONSE] [REQ-1790926737531-54] Status: 200 OK

[RESPONSE] [REQ-1790926737531-54] Type: PageImpl
```

This is useful for tracing a request through the application.

### Search for response logs

```text
message: "RESPONSE"
```

### Search for errors

```text
message: "ERROR"
```

### Search for HTTP 500

```text
message: "Status: 500"
```

### Search for a specific response type

```text
message: "PageImpl"
```

## 7. Why Centralized Logging?

Without centralized logging:

```text
Service A
    │
    ▼
service-a.log

Service B
    │
    ▼
service-b.log

Service C
    │
    ▼
service-c.log
```

We would need to search each application's logs separately.

With ELK:

```text
Service A ──┐
            │
Service B ──┼──> Logstash ──> Elasticsearch ──> Kibana
            │
Service C ──┘
```

All logs can be searched from one place.

## 8. Microservices Example

Imagine the following architecture:

```text
                   API Gateway
                       │
          ┌────────────┼────────────┐
          │            │            │
          ▼            ▼            ▼
      Order Service Product Service Payment Service
```

A single request could generate:

```text
Gateway
REQ-123 → /orders/100

Order Service
REQ-123 → Creating order

Product Service
REQ-123 → Checking inventory

Payment Service
REQ-123 → Processing payment

Order Service
REQ-123 → Order created
```

If all services send their logs to Elasticsearch, we can search:

```text
"REQ-123"
```

and see the complete request flow.

This is one of the major benefits of centralized logging.

## 9. Current POC vs Production-Ready Logging

### Current POC

```text
Spring Boot
    │
    ▼
Logback JSON
    │
    ▼
Logstash
    │
    ▼
Elasticsearch
    │
    ▼
Kibana
```

The current log is still largely represented by the `message` field:

```json
{
  "message": "[RESPONSE] [REQ-1790926737531-54] Status: 200 OK, Type: PageImpl"
}
```

### Production-style structured logging

Ideally we want:

```json
{
  "timestamp": "2026-10-02T13:08:57.539Z",
  "service": "product-service",
  "environment": "prod",
  "level": "INFO",
  "event": "RESPONSE",
  "requestId": "REQ-1790926737531-54",
  "status": 200,
  "statusText": "OK",
  "responseType": "PageImpl"
}
```

Now Kibana can search individual fields.

For example:

```text
requestId: "REQ-1790926737531-54"
```

```text
status: 500
```

```text
event: RESPONSE
```

```text
service: product-service
```

This is much more powerful than searching a raw log string.

## 10. Future Dashboard

Once the logs are structured, we can create a Kibana dashboard containing:

```text
┌──────────────────────────────────────────────┐
│              PRODUCT SERVICE                 │
├────────────┬────────────┬────────────────────┤
│ Requests   │ Errors     │ Avg Response Time  │
│ 125,430    │ 342        │ 185 ms             │
├────────────┴────────────┴────────────────────┤
│                                              │
│       Requests / Errors over time            │
│                                              │
├──────────────────────────────────────────────┤
│ Top failing endpoints                        │
│                                              │
│ /products       120 errors                   │
│ /orders          83 errors                   │
│ /users           45 errors                   │
└──────────────────────────────────────────────┘
```

## Key Takeaways

| Component | Responsibility |
|---|---|
| Spring Boot | Generates application logs |
| Logback | Formats and sends logs |
| Logstash | Receives, transforms and forwards logs |
| Elasticsearch | Stores and indexes logs |
| Kibana | Searches and visualizes logs |

The overall architecture is:

```text
Spring Boot
     │
     │ JSON logs
     ▼
  Logback
     │
     │ TCP :5000
     ▼
  Logstash
     │
     │ Process / Transform
     ▼
Elasticsearch
     │
     │ Query
     ▼
  Kibana
     │
     ▼
Developer / Operations
```

The next improvement for this POC is to make the `ControllerLoggingAspect` produce **structured fields** such as `requestId`, `event`, `status`, `endpoint`, and `responseTime`, rather than keeping everything inside `message`.