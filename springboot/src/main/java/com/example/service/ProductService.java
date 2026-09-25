package com.example.service;

import com.example.entity.Product;
import com.example.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final RedisCacheService cacheService;

    public ProductService(ProductRepository productRepository, RedisCacheService cacheService) {
        this.productRepository = productRepository;
        this.cacheService = cacheService;
    }

    public Product getProduct(Long id) {
        String key = "product:" + id;
        // Check Redis cache first
        Product cached = cacheService.get(key, Product.class);
        if (cached != null) {
            log.info("Cache hit for product id: {}", id);
            return cached;
        }

        // Get from DB
        Product product = productRepository.findById(id).orElseThrow();

        // Update Redis cache
        cacheService.set(key, product, 10, TimeUnit.MINUTES);
        return product;
    }
}
