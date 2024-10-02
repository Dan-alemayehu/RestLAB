package com.astontech.rest.services;

import com.astontech.rest.domain.Product;

import java.util.Optional;

public interface ProductService {

    Product findBySkuOrId (String sku, Integer id);

    Iterable<Product> findAll();
    Product saveProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(Product product);
    void deleteProduct(Integer id);


}
