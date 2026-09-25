package com.example.service;

import com.example.dto.product.ProductPageResponse;
import com.example.entity.Product;
import com.example.kafka.event.ProductEvent;
import com.example.kafka.event.producer.ProductEventProducer;
import com.example.repository.ProductRepository;

import lombok.extern.log4j.Log4j2;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Cacheable(value = "product", key = "#id")
    public Product getProduct(Long id) {
        log.info("Product {} queried for {} times", id, incrementQueryCount(id));
        return productRepository.findById(id).orElseThrow();
    }

    private Long incrementQueryCount(Long id) {
        String key = "product:query-count:" + id;
        return redis.increment(key);
    }

    @Cacheable(value = "products", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    // Using our custom ProductPageResponse as Redis is not able to serialize Spring's Page object
    // Else we would have used Page<Product> as return type
    public ProductPageResponse listProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return new ProductPageResponse(
                productPage.getContent(),
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages());
    }

    @CacheEvict(value = "products", allEntries = true)
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

    @CacheEvict(value = "products", allEntries = true)
    public Product save(Product product) {
        Product saved = productRepository.save(product);
        ProductEvent event =
                new ProductEvent(
                        "PRODUCT_CREATED", saved.getId(), saved.getName(), saved.getPrice());
        eventProducer.publish(event);
        return saved;
    }

    private String getCacheKey(Long productId) {
        return "product:" + productId;
    }
}
