package com.ecommerce.product.services.client;

import com.ecommerce.product.dtos.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value="order-service", url="http://localhost:8110")
public interface OrderFeignClient {

    @GetMapping(value = "/api/order/fetch",consumes = "application/json")
    public ResponseEntity<OrderDTO> fetchOrderDetails(@RequestParam String mobileNumber);

}
