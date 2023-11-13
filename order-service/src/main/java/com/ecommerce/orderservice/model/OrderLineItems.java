package com.ecommerce.orderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "order_line_item_list")
public class OrderLineItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_line_item_id")
    private Long id ;
    @Column(name = "order_line_item_skucode")
    private  String skuCode ;
    @Column(name = "order_line_item_price")
    private BigDecimal price ;
    @Column(name = "order_line_item_quantity")
    private Integer quantity ;
}
