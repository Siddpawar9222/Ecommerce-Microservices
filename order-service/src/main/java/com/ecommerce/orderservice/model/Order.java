package com.ecommerce.orderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "order_list")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "order_number")
    private String orderNumber;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "order_orderlineitem_map" ,joinColumns = @JoinColumn(name = "order_id"),inverseJoinColumns = @JoinColumn(name = "order_line_item_id"))
    private List<OrderLineItems> orderLineItemsList;
}
