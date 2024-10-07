package com.astontech.rest.services;

import com.astontech.rest.domain.Cart;

public interface CartService {

    Cart findByUsername(String username);
    Cart findById(String id);
    Cart saveCart(Cart cart);
    void deleteCart(String id);

    Cart updateItems(Cart cart, Integer productId, Integer Quantity);
}
