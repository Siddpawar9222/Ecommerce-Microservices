package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.InventoryResponse;
import com.ecommerce.orderservice.dto.OrderLineItemsDto;
import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.event.OrderPlacedEvent;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.model.OrderLineItems;
import com.ecommerce.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;

    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate ;

    @Transactional
     public  void placeOrder(OrderRequest orderRequest) throws Exception {


         List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDto()
                 .stream().map(this::mapToDto).toList();

         Order order = Order.builder()
                 .orderNumber(UUID.randomUUID().toString())
                 .orderLineItemsList(orderLineItems)
                 .build() ;

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();


     InventoryResponse[] inventoryResponses  = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        System.out.printf(Arrays.toString(inventoryResponses));

        if (inventoryResponses != null) {
            boolean allInventoryInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);
            if(allInventoryInStock){
                orderRepository.save(order) ;
                kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber())) ;
            }else {
                throw new IllegalArgumentException("Product is not in Stock. Please try it later") ;
            }
        }else{
            throw new Exception("No data Found") ;
        }


     }
      private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
         return OrderLineItems.builder()
                 .skuCode(orderLineItemsDto.getSkuCode())
                 .price(orderLineItemsDto.getPrice())
                 .quantity(orderLineItemsDto.getQuantity())
                 .build();
    }
}
