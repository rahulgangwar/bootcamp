package com.example.grpc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProductGrpcClientTest {

    @Autowired private ProductGrpcClient productGrpcClient;

    @Test
    void shouldGetProduct() {
        Product product = productGrpcClient.getProduct(1L);
        System.out.println("Product ID: " + product.getId());
        System.out.println("Product Name: " + product.getName());
        System.out.println("Product Price: " + product.getPrice());

        assertEquals(1L, product.getId());
        assertEquals("iPhone 17", product.getName());
        assertEquals("79999.00", product.getPrice());
    }
}
