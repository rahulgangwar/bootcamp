package com.example.dto.product;

import java.math.BigDecimal;

public record ProductUpdateRequest(
        String name,
        BigDecimal price
) {}