package com.example.dto.product;

import com.example.entity.Product;
import java.util.List;

public record ProductPageResponse(
        List<Product> products, int page, int size, long totalElements, int totalPages) {}
