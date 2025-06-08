package com.ecommerce.order.dtos;

import lombok.*;


@Data
@Getter
@Setter
public class CartItemRequest {
    private String productId;
    private Integer quantity;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
