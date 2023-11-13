package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductRequest;
import com.ecommerce.productservice.dto.ProductResponse;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor     //*
@Slf4j                       //**
public class ProductService {

    private final ProductRepository productRepository ;

    @Transactional
    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product) ;
        log.info("Project {} is saved " ,product.getId());
    }

    @Transactional
    public List<ProductResponse> getAllProducts(){
        List<Product> productList = productRepository.findAll();
       return  productList.stream().map(this::mapToProductResponse).toList() ;
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build() ;
    }
}

/*
 //* --> We are using construction injection
 //**-->@Slf4 : I can print log in console. Annotation of Lombok
*/