package com.example.controller;

import com.example.dto.ProductUpdateRequest;
import com.example.entity.Product;
import com.example.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        return productService.updateProduct(id, request.name(), request.price());
    }
}
