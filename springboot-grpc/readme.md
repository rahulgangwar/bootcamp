# gRPC POC – Spring Boot

## 1. Architecture

```text
                    gRPC / HTTP2
┌─────────────────┐  ───────────────►  ┌────────────────────┐
│     CLIENT      │                    │       SERVER       │
│                 │                    │                    │
│ ProductGrpcClient                    │ ProductGrpcService │
│                 │                    │                    │
└────────┬────────┘                    └─────────┬──────────┘
         │                                       │
         ▼                                       ▼
 ProductServiceGrpc                    ProductServiceImplBase
 ProductServiceBlockingStub             @GrpcService
         │                                       │
         └───────────────┬───────────────────────┘
                         │
                    product.proto
                      CONTRACT
```

The `.proto` file is the **contract between client and server**.

---

# 2. Contract – `product.proto`

```protobuf
syntax = "proto3";

option java_multiple_files = true;
option java_package = "com.example.grpc";

service ProductService {

    rpc GetProduct(GetProductRequest) returns (Product);
}

message GetProductRequest {
    int64 id = 1;
}

message Product {
    int64 id = 1;
    string name = 2;
    string price = 3;
}
```

From this protobuf definition, Java classes are generated:

```text
Product
GetProductRequest
ProductServiceGrpc
```

The important generated class for the client is:

```text
ProductServiceGrpc
        │
        └── ProductServiceBlockingStub
```

---

# 3. Server

The server implements the generated service base class:

```java
@GrpcService
public class ProductGrpcService
        extends ProductServiceGrpc.ProductServiceImplBase {

    @Override
    public void getProduct(
            GetProductRequest request,
            StreamObserver<Product> responseObserver) {

        Product product = Product.newBuilder()
                .setId(request.getId())
                .setName("iPhone 17")
                .setPrice("79999.00")
                .build();

        responseObserver.onNext(product);
        responseObserver.onCompleted();
    }
}
```

The generated class provides:

```java
ProductServiceImplBase
```

Our implementation extends it:

```text
ProductServiceImplBase
        ↑
        │ extends
        │
ProductGrpcService
```

The server implements the RPC defined in the `.proto`:

```protobuf
rpc GetProduct(GetProductRequest) returns (Product);
```

Spring exposes the class as a gRPC service because of:

```java
@GrpcService
```

The gRPC server listens on:

```text
localhost:9090
```

---

# 4. Client

The client uses the generated blocking stub:

```java
@Component
@ImportGrpcClients(
        target = "static://localhost:9090",
        types = ProductServiceGrpc.ProductServiceBlockingStub.class
)
public class ProductGrpcClient {

    private final ProductServiceGrpc.ProductServiceBlockingStub stub;

    public ProductGrpcClient(
            ProductServiceGrpc.ProductServiceBlockingStub stub) {
        this.stub = stub;
    }

    public Product getProduct(Long id) {

        GetProductRequest request =
                GetProductRequest.newBuilder()
                        .setId(id)
                        .build();

        return stub.getProduct(request);
    }
}
```

The important object here is:

```java
ProductServiceGrpc.ProductServiceBlockingStub
```

This is **generated code**.

We don't implement the network communication ourselves.

---

# 5. What exactly happens in `stub.getProduct(request)`?

This is the most important part of the POC.

When we write:

```java
return stub.getProduct(request);
```

it looks like an ordinary Java method call.

But it is actually the entry point into the generated gRPC client machinery.

The simplified flow is:

```text
stub.getProduct(request)
        │
        ▼
Generated gRPC client method
        │
        ▼
ClientCalls.blockingUnaryCall(...)
        │
        ▼
gRPC Channel
        │
        ▼
HTTP/2 connection
        │
        ▼
localhost:9090
        │
        ▼
gRPC Server
```

Let's break it down.

---

# 6. Step 1 – Build the request

Before calling the stub:

```java
GetProductRequest request =
        GetProductRequest.newBuilder()
                .setId(1L)
                .build();
```

We now have a protobuf object:

```text
GetProductRequest
    id = 1
```

This is not JSON.

It is a protobuf-generated Java object.

---

# 7. Step 2 – Call the generated stub

We execute:

```java
stub.getProduct(request);
```

The method exists because protobuf generated the gRPC client code from:

```protobuf
rpc GetProduct(GetProductRequest) returns (Product);
```

Conceptually, the generated method looks roughly like:

```java
public Product getProduct(GetProductRequest request) {

    return ClientCalls.blockingUnaryCall(
            channel,
            METHOD_GET_PRODUCT,
            CallOptions.DEFAULT,
            request
    );
}
```

The actual generated code contains more details, but this is the important mental model.

The generated method knows:

```text
RPC method
    ↓
ProductService/GetProduct
```

and:

```text
Request type
    ↓
GetProductRequest

Response type
    ↓
Product
```

---

# 8. Step 3 – `ClientCalls.blockingUnaryCall(...)`

Because we are using:

```java
ProductServiceBlockingStub
```

this is a **blocking unary RPC**.

Conceptually:

```text
blockingUnaryCall()
        │
        ├── create gRPC call
        │
        ├── serialize request
        │
        ├── send request
        │
        ├── wait for response
        │
        ├── deserialize response
        │
        └── return Product
```

The word `blocking` is important.

The calling thread waits until:

```text
response received
        OR
error
        OR
deadline/timeout
```

---

# 9. Step 4 – gRPC Channel

The stub does not directly open a TCP connection itself.

It uses a gRPC:

```text
Channel
```

Conceptually:

```text
Stub
 │
 ▼
Channel
 │
 ▼
Connection
```

The channel knows where the server is:

```text
static://localhost:9090
```

So the destination is:

```text
localhost
port 9090
```

The channel manages the underlying connection and gRPC communication.

---

# 10. Step 5 – Serialize the protobuf request

The request:

```java
GetProductRequest
```

must be converted into bytes before it can travel over the network.

Conceptually:

```text
GetProductRequest
       │
       ▼
Protobuf serialization
       │
       ▼
Binary bytes
```

For example:

```text
Java Object
     ↓
Protobuf
     ↓
Binary representation
```

This is one of the major differences from a typical REST/JSON call.

REST:

```text
Java Object
     ↓
JSON
     ↓
UTF-8 bytes
```

gRPC:

```text
Java Object
     ↓
Protocol Buffers
     ↓
Binary bytes
```

---

# 11. Step 6 – HTTP/2 transport

The serialized protobuf data is sent through gRPC over HTTP/2.

Conceptually:

```text
ProductGrpcClient
       │
       ▼
Generated Stub
       │
       ▼
gRPC Channel
       │
       ▼
HTTP/2
       │
       ▼
localhost:9090
```

So:

```text
gRPC
```

is not itself the network transport protocol.

The typical stack is:

```text
Application
     │
     ▼
gRPC
     │
     ▼
HTTP/2
     │
     ▼
TCP
     │
     ▼
IP
```

---

# 12. Step 7 – Server receives the request

The gRPC server receives the HTTP/2 request.

Spring gRPC has already registered:

```text
ProductService
```

and knows that:

```text
GetProduct
```

maps to our:

```java
ProductGrpcService.getProduct(...)
```

So the request eventually reaches:

```java
@Override
public void getProduct(
        GetProductRequest request,
        StreamObserver<Product> responseObserver)
```

The request is reconstructed as:

```text
GetProductRequest
    id = 1
```

---

# 13. Step 8 – Server executes business logic

Our current POC does:

```java
Product product = Product.newBuilder()
        .setId(request.getId())
        .setName("iPhone 17")
        .setPrice("79999.00")
        .build();
```

In a real application this would typically be:

```text
gRPC Service
      │
      ▼
Service / Business Layer
      │
      ▼
Repository
      │
      ▼
Database
```

For example:

```text
ProductGrpcService
       ↓
ProductService
       ↓
ProductRepository
       ↓
MySQL
```

---

# 14. Step 9 – Server sends the response

The server sends the response using:

```java
responseObserver.onNext(product);
responseObserver.onCompleted();
```

For a unary RPC:

```text
one request
     ↓
one response
```

The response is:

```text
Product
```

It is serialized using protobuf:

```text
Product Java object
       ↓
Protobuf serialization
       ↓
Binary bytes
```

---

# 15. Step 10 – Response travels back

The response travels back through:

```text
Server
   │
   ▼
HTTP/2
   │
   ▼
gRPC Channel
   │
   ▼
Client
```

The client deserializes the protobuf bytes:

```text
Binary bytes
     ↓
Protobuf deserialization
     ↓
Product Java object
```

---

# 16. Step 11 – `blockingUnaryCall()` returns

Eventually:

```java
stub.getProduct(request)
```

returns:

```java
Product
```

So:

```java
Product product = stub.getProduct(request);
```

gives us the generated protobuf object:

```text
Product
 ├── id
 ├── name
 └── price
```

The original calling thread can now continue.

---

# 17. Complete internal flow

The entire call can be visualized as:

```text
CLIENT
────────────────────────────────────────────────────────────

productGrpcClient.getProduct(1L)
             │
             ▼
     Build GetProductRequest
             │
             ▼
     stub.getProduct(request)
             │
             ▼
   Generated BlockingStub
             │
             ▼
 ClientCalls.blockingUnaryCall()
             │
             ▼
         Channel
             │
             ▼
    Protobuf serialization
             │
             ▼
          HTTP/2
             │
             │
═════════════ NETWORK ═════════════
             │
             │
             ▼
          HTTP/2
             │
             ▼
    gRPC Server / Netty
             │
             ▼
   Deserialize protobuf
             │
             ▼
 ProductGrpcService
             │
             ▼
      getProduct()
             │
             ▼
      Create Product
             │
             ▼
 responseObserver.onNext()
             │
             ▼
    Protobuf serialization
             │
             ▼
          HTTP/2
             │
═════════════ NETWORK ═════════════
             │
             ▼
      gRPC Client
             │
             ▼
   Deserialize protobuf
             │
             ▼
 ClientCalls.blockingUnaryCall()
             │
             ▼
         Product
             │
             ▼
       Test / Caller
```

---

# 18. Why it looks like a normal Java method

This is one of the nicest parts of gRPC.

The application developer writes:

```java
Product product = stub.getProduct(request);
```

instead of manually dealing with:

```text
HTTP URL
HTTP method
HTTP headers
JSON serialization
HTTP client
JSON deserialization
error parsing
```

The generated stub hides those details.

So the developer sees:

```text
Remote service
      ↓
Looks like a Java method
```

while underneath:

```text
Java method
    ↓
Generated gRPC code
    ↓
gRPC runtime
    ↓
HTTP/2
    ↓
Network
    ↓
Remote service
```

This is the essence of **Remote Procedure Call (RPC)**.

---

# 19. Very important interview distinction

Do not say:

> "The client calls `ProductGrpcService.getProduct()`."

That's not quite correct.

The client calls:

```java
stub.getProduct(request);
```

The gRPC framework sends the request over the network.

The **server-side gRPC runtime** then invokes:

```java
ProductGrpcService.getProduct(...)
```

So:

```text
Client JVM                         Server JVM

stub.getProduct()
       │
       │
       │      Network
       ├───────────────────────►
       │                         │
       │                         ▼
       │                  ProductGrpcService
       │                  .getProduct()
       │                         │
       │                         ▼
       │                      response
       ◄─────────────────────────┤
```

There is no direct Java object reference from the client to the server.

That is the key idea to reme
