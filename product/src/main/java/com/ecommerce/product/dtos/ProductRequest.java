package com.ecommerce.product.dtos;

import com.ecommerce.product.models.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class ProductRequest {

    @NotBlank(message = "Product Name is required")
    private String name;
    @NotBlank(message = "Product Description is required")
    private String description;
    @NotBlank(message = "Product Brand is required")
    private String brand;
    private double price;
    private Integer stockQuantity;
    private Category category;
    private String imageUrl;
    private double discount;
    public boolean isCreated;
}
