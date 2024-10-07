package com.astontech.rest.configuration;

import com.astontech.rest.domain.Cart;
import com.astontech.rest.domain.CartItem;
import com.astontech.rest.repositories.CartRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
//@Profile("dev")
public class SeedData implements CommandLineRunner {

    private CartRepo cartRepo;

    public SeedData(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (cartRepo.findAll().size() == 0) {
            List<String> names = Arrays.asList("bip", "jay25", "strey");

            names.forEach( name -> {
                Cart cart = new Cart();
                cart.setPurchased(false);
                cart.setUsername(name);
                CartItem cartItem1 = new CartItem("TV-134",3, new BigDecimal("189.99"));
                CartItem cartItem2 = new CartItem("REMOTE-74",1, new BigDecimal("17.85"));
                CartItem cartItem3 = new CartItem("TV-MOUNT-A32",5, new BigDecimal("45.49"));
                cart.setItems(new ArrayList<>(Arrays.asList(cartItem1, cartItem2, cartItem3)));
                cartRepo.save(cart);
            });

            cartRepo.findAll().forEach(System.out::println);

        }
    }

}
