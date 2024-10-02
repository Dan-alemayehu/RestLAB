package com.astontech.rest.controllers;

import com.astontech.rest.domain.Product;
import com.astontech.rest.exceptions.ProductNotFoundException;
import com.astontech.rest.repositories.ProductRepo;
import com.astontech.rest.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/product")
public class ProductRest {

    private ProductService productService;

    public ProductRest(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<Product>> findProduct() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping()
    public ResponseEntity<Product> findByIdOrSku(@RequestParam(required = false) Integer id,
                                                 @RequestParam(required = false) String sku) {
        return ResponseEntity.ok(productService.findBySkuOrId(sku, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findByIdInPath(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.findBySkuOrId(null, id));
    }

    @GetMapping("/") // <-- http://localhost:8080/product/
    public Iterable<Product> getAllProducts() {
        return productService.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {

        return new ResponseEntity<>(
                productService.saveProduct(product),
                HttpStatus.CREATED
        );
    }

    //Idempotent - multiple requests will not change the system
    @PutMapping("/")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        return new ResponseEntity<>(
                productService.saveProduct(product),
                HttpStatus.ACCEPTED
        );
    }

    @DeleteMapping("/")
    public void deleteProduct(@RequestBody Product product) {
        productService.deleteProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }

    //Query Params
//    @GetMapping() //http://localhost:8080/product?id=3
//    public Product findByIdOrSku(@RequestParam(required = false) Integer id,
//                                 @RequestParam(required = false) String sku) {
//        if (sku != null && !sku.isEmpty()) {
//            return productRepo.findBySku(sku)
//                    .orElseThrow(() -> new ProductNotFoundException(sku));
//        } else if (id != null) {
//            return productRepo.findById(id)
//                    .orElseThrow(() -> new ProductNotFoundException(Integer.toString(id)));
//        } else {
//            //throw custom exception
//            throw new ProductNotFoundException((sku == null ? Integer.toString(id) : sku));
//        }
//    }

    //Path Param
//    @GetMapping("/{id}")
//    public Product findByIdInPath(@PathVariable Integer id) throws ProductNotFoundException {
////        Optional<Product> productOptional = productRepo.findById(id);
////        return productOptional.orElseThrow(() -> new ProductNotFoundException(Integer.toString(id)));
//        return productRepo.findById(id)
//                .orElseThrow(() -> new ProductNotFoundException(Integer.toString(id)));
//    }
}
