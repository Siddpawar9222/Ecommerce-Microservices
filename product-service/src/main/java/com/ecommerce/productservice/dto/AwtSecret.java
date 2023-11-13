package com.ecommerce.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AwtSecret {
 private String   username;
 private String   password;
 private String   host;
 private String   engine;
 private String   port;
 private String   dbInstanceIdentifier ;
}
