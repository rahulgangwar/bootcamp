package com.example.service;

import com.example.entity.Product;
import com.example.kafka.event.ProductEvent;
import com.example.kafka.event.producer.ProductEventProducer;
import com.example.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final RedisCacheService redis;
    private final ProductEventProducer eventProducer;

    public ProductService(
            ProductRepository productRepository,
            RedisCacheService redis,
            ProductEventProducer eventProducer) {
        this.productRepository = productRepository;
        this.redis = redis;
        this.eventProducer = eventProducer;
    }

    public Product getProduct(Long id) {
        // Check Redis cache first
        Product cached = redis.get(getCacheKey(id), Product.class);
        if (cached != null) {
            return cached;
        }

        // Get from DB
        Product product = productRepository.findById(id).orElseThrow();

        // Update Redis cache
        redis.set(getCacheKey(id), product, 10, TimeUnit.MINUTES);
        return product;
    }

    public Product updateProduct(Long id, String name, BigDecimal price) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(name);
        product.setPrice(price);

        Product updated = productRepository.save(product);
        ProductEvent event =
                new ProductEvent(
                        "PRODUCT_UPDATED", updated.getId(), updated.getName(), updated.getPrice());
        eventProducer.publish(event);
        redis.delete(getCacheKey(id));
        return updated;
    }

    private String getCacheKey(Long productId) {
        return "product:" + productId;
    }
}
