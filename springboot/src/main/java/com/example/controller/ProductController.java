package com.example.controller;

import com.example.dto.product.ProductPageResponse;
import com.example.dto.product.ProductUpdateRequest;
import com.example.entity.Product;
import com.example.service.ProductService;

import lombok.extern.log4j.Log4j2;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@Log4j2
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

    @GetMapping
    public ProductPageResponse listProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return productService.listProducts(PageRequest.of(page, size));
    }

    @PostMapping
    public Product createProduct(Product product) {
        return productService.save(product);
    }
}
