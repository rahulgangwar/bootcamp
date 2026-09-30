package com.example.service;

import com.example.entity.Product;
import com.example.repository.ProductRepository;

import lombok.extern.log4j.Log4j2;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow();
    }
}
