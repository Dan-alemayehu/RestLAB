package com.astontech.rest.exceptions;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String id) {
        super("Could not find product " + id);
    }
}
