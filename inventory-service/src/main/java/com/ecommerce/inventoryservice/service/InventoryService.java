package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.dto.InventoryResponse;
import com.ecommerce.inventoryservice.model.Inventory;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional
    public List<InventoryResponse> isInStock(List<String> skucode){
        List<Inventory> inventories = inventoryRepository.findBySkuCodeIn(skucode) ;

       if(inventories.isEmpty()){
            throw new IllegalArgumentException("Data Not Found") ;
       }
       return
                inventories.stream()
                .map(this::mapToDto)
                .toList();
    }

    private InventoryResponse mapToDto(Inventory inventory) {
         return InventoryResponse.builder()
                 .skuCode(inventory.getSkuCode())
                 .isInStock(inventory.getQuantity()>0)
                 .build();
    }

}
