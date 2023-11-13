package com.ecommerce.orderservice.webconfig;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    //Only for One Instance of Inventory
//    @Bean
//    public WebClient webClient(){
//        return WebClient.builder().build() ;
//    }

    //Client Side Load Balancing
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder()  ;
    }
}
