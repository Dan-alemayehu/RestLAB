package com.astontech.rest.controllers;

import com.astontech.rest.domain.Cart;
import com.astontech.rest.repositories.CartRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@Profile("dev")
public class TestRest {

    private CartRepo cartRepo;

    public TestRest(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    @GetMapping("/test/cart/")
    public List<Cart> getallcarts(){
        return cartRepo.findAll();
    }

    @GetMapping("/test")
    public String welcomeDevs() {
        return "Welcome back developers!";
    }

    @GetMapping
    public void exceptionTest() {
        throw new RuntimeException();
    }
}
