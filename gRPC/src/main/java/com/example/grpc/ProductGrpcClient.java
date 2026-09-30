package com.example.grpc;

import org.springframework.grpc.client.ImportGrpcClients;
import org.springframework.stereotype.Component;

@Component
@ImportGrpcClients(
        target = "static://localhost:9090",
        types = ProductServiceGrpc.ProductServiceBlockingStub.class)
public class ProductGrpcClient {

    private final ProductServiceGrpc.ProductServiceBlockingStub stub;

    public ProductGrpcClient(ProductServiceGrpc.ProductServiceBlockingStub stub) {
        this.stub = stub;
    }

    public Product getProduct(Long id) {
        GetProductRequest request = GetProductRequest.newBuilder().setId(id).build();
        return stub.getProduct(request);
    }
}
