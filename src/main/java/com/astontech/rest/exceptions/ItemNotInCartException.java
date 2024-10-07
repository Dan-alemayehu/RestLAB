package com.astontech.rest.exceptions;

public class ItemNotInCartException extends RuntimeException{

    public ItemNotInCartException(String msg){
        super(msg);
    }
}
