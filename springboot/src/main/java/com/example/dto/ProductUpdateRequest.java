package com.example.dto;

import java.math.BigDecimal;

public record ProductUpdateRequest(
        String name,
        BigDecimal price
) {}