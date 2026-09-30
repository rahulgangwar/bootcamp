package com.example.service;

import com.example.grpc.GetProductRequest;
import com.example.grpc.Product;
import com.example.grpc.ProductServiceGrpc;

import io.grpc.stub.StreamObserver;

import lombok.extern.log4j.Log4j2;
import org.springframework.grpc.server.service.GrpcService;

@Log4j2
@GrpcService
public class ProductGrpcService extends ProductServiceGrpc.ProductServiceImplBase {

    @Override
    public void getProduct(GetProductRequest request, StreamObserver<Product> responseObserver) {
        log.info("gRPC : Get product id: {}", request.getId());
        Product product =
                Product.newBuilder()
                        .setId(request.getId())
                        .setName("iPhone 17")
                        .setPrice("79999.00")
                        .build();

        responseObserver.onNext(product);
        responseObserver.onCompleted();
    }
}
