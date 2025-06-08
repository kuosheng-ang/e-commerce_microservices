package com.ecommerce.order.clients;

import com.ecommerce.order.dtos.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

//@HttpExchange
@FeignClient(value="product-service", url="http://localhost:8080")
public interface ProductServiceClient {

    //@GetExchange("/api/products/{id}")
    @GetMapping(value = "/api/products/{id}",consumes = "application/json")
    ProductResponse getProductDetails(@PathVariable("id") String productId);
}
