package com.astontech.rest.exceptions;

public class OutOfStockException extends RuntimeException{

    public OutOfStockException(String msg){
        super(msg);
    }
}
