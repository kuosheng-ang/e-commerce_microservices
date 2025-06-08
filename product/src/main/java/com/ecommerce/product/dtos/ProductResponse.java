package com.ecommerce.product.dtos;

import com.ecommerce.product.models.Category;
import lombok.Data;
import lombok.*;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Getter
@Setter
public class ProductResponse {

    private List<ProductDTO> content;
    private Long id;
    private String name;
    private String description;
    private double price;
    private String brand;
    private Integer stockQuantity;
    private Category category;
    private String imageUrl;
    private Boolean active;

    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private boolean lastPage;
}
