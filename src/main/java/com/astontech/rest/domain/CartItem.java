package com.astontech.rest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    String productSku;
    Integer quantity;
    BigDecimal price;

    public void modifyQuantity(Integer val) {
        this.quantity += val;
    }
}
