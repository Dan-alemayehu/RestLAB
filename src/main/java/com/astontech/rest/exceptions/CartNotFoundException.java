package com.astontech.rest.exceptions;

public class CartNotFoundException extends RuntimeException{

    public CartNotFoundException(String cartId){
        super("Cart: " + cartId + " not found.");
    }
}
