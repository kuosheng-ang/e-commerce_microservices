package com.ecommerce.order.dtos;

import lombok.*;

import java.math.BigDecimal;

@Data

public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    public Integer quantity;
    private Integer stockQuantity;
    private String category;
    private String imageUrl;
    private Boolean active;
}
