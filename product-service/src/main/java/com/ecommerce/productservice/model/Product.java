package com.ecommerce.productservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "product_list")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id ;
    @Column(name = "product_name")
    private String name ;
    @Column(name = "product_description")
    private String description ;
    @Column(name = "product_price")
    private BigDecimal price ;
}
