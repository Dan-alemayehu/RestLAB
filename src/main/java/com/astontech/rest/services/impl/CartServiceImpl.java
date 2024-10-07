package com.astontech.rest.services.impl;

import com.astontech.rest.domain.Cart;
import com.astontech.rest.domain.CartItem;
import com.astontech.rest.domain.Product;
import com.astontech.rest.exceptions.CartNotFoundException;
import com.astontech.rest.exceptions.ItemNotInCartException;
import com.astontech.rest.exceptions.OutOfStockException;
import com.astontech.rest.repositories.CartRepo;
import com.astontech.rest.repositories.ProductRepo;
import com.astontech.rest.services.CartService;
import com.astontech.rest.services.ProductService;

import java.math.BigDecimal;

public class CartServiceImpl implements CartService {

    private CartRepo cartRepo;
    private ProductService productService;

    public CartServiceImpl(CartRepo cartRepo, ProductService productService) {
        this.cartRepo = cartRepo;
        this.productService = productService;
    }

    @Override
    public Cart findByUsername(String username) {

        return cartRepo.findByUsername(username)
                .orElseThrow(() -> new CartNotFoundException(username));
    }

    @Override
    public Cart findById(String id) {

        return cartRepo.findById(id)
                .orElseThrow(() -> new CartNotFoundException(id));
    }

    @Override
    public Cart saveCart(Cart cart) {
        return cartRepo.save(cart);
    }

    @Override
    public void deleteCart(String id) {
        cartRepo.deleteById(id);

    }

    @Override
    public Cart updateItems(Cart cart, Integer productId, Integer quantity) {
        Product product = productService.findBySkuOrId(null, productId);

        if (quantity > 0) {
            // addItemToCart

        } else if (quantity < 0) {
            // removeItemFromCart
            return removeItemFromCart(cart, product, quantity);

        }


        return cart;
    }

    private Cart addItemToCart(Cart cart, Product product, Integer quantity) {
        //check that this is enough product
        if (product.getQuantity() < quantity) {
            throw new OutOfStockException("Not enough: " + product.getSku() + "[requested: " + quantity + " only have " + product.getQuantity() + "]");
        } else {
            //if CartItem exist add to quantity
            if(checkCartForItem(cart, product)) {
                incrementCartItemFromSku(cart, product.getSku(), quantity);
            } else {
                // create new CartItem
                CartItem cartItem = new CartItem(product.getSku(), quantity, new BigDecimal(product.getPrice()));
                cart.getItems().add(cartItem);
            }
            cartRepo.save(cart);
            product.setQuantity(product.getQuantity() - quantity);
            productService.saveProduct(product);
        }
        return cart;
    }

    public Cart removeItemFromCart(Cart cart, Product product, Integer quantity){
        // check if product exist in cart
        if(checkCartForItem(cart, product)){
            //decrement quantity
            incrementCartItemFromSku(cart, product.getSku(), quantity);
            //clean cart
            cart.clearItemsWithQuantityLessThanOne();
            cartRepo.save(cart);
            // add removed quantity from cart back to product
            product.setQuantity(product.getQuantity() + quantity);
            productService.saveProduct(product);
        } else {
            throw new ItemNotInCartException("Item: " + product.getSku() + " not in cart.");
        }
        return cart;
    }

    private boolean checkCartForItem(Cart cart, Product product) {
        return cart.getItems()
                .stream()
                .anyMatch(item -> item.getProductSku().equals(product.getSku()));
    }

    private void incrementCartItemFromSku(Cart cart, String sku, Integer quantity) {
        cart.getItems()
                .stream()
                .filter(item -> item.getProductSku().equals(sku))
                .findFirst()
                .get()
                .modifyQuantity(quantity);
    }
}
