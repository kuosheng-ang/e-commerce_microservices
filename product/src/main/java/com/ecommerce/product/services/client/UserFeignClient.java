package com.ecommerce.product.services.client;

import com.ecommerce.product.dtos.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value="user-service", url="http://localhost:8082")
public interface UserFeignClient {

    @GetMapping(value = "/api/user/fetch",consumes = "application/json")
    public ResponseEntity<OrderDTO> fetchOrderDetails(@RequestParam String mobileNumber);

}
