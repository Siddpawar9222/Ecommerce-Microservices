package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.service.OrderService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private  final OrderService orderService ;


      @ResponseStatus(HttpStatus.ACCEPTED)
      @PostMapping()
    @Retry(name = "inventory" , fallbackMethod = "fallBackMethod")
//    @CircuitBreaker(name = "inventory",fallbackMethod = "fallBackMethod")
//     @TimeLimiter(name="inventory",fallbackMethod = "fallBackMethod")
//      @Bulkhead(name="inventory",fallbackMethod = "fallBackMethod")
     public String placeOrder(@RequestBody OrderRequest orderRequest) throws Exception {
         orderService.placeOrder(orderRequest);
        return "Order Placed Successfully" ;
    }
    public  String fallBackMethod(OrderRequest orderRequest,Exception e){
         return "OOPS! Something went wrong, Please order after some time" ;
    }

}
