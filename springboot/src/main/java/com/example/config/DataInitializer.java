package com.example.config;

import com.example.entity.Product;
import com.example.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initProducts(ProductRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }
            List<Product> products = new ArrayList<>();
            for (long i = 1; i <= 10_000; i++) {
                Product product = new Product();
                product.setName("Product " + i);
                product.setPrice(BigDecimal.valueOf(i * 10));
                products.add(product);
            }
            repository.saveAll(products);
        };
    }
}
