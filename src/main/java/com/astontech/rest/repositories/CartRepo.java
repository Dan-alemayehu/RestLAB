package com.astontech.rest.repositories;

import com.astontech.rest.domain.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepo extends CrudRepository<Cart, String> {

    List<Cart> findAll();
    Optional<Cart> findByUsername (String username);
}
