package com.astontech.rest.repositories;

import com.astontech.rest.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepo extends CrudRepository<Product, Integer> {

    Optional<Product> findBySku(String sku);
    Optional<Product> findBySkuOrId (String sku, Integer id);
}
