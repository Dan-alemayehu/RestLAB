package com.astontech.rest.domain;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.util.List;
import java.util.stream.Collectors;

@Data
@RedisHash("cart")
public class Cart {

    // region ATTRIBUTES
    @Id
    private String id;

    @Indexed
    String username;

    List<CartItem> items;
    Boolean purchased;
    //endregion

    //region HELPER METHODS
    public void clearItemsWithQuantityLessThanOne(){
        this.items = this.getItems()
                .stream()
                .filter(item -> item.getQuantity() > 0)
                .collect(Collectors.toList());
    }
    //endregion

}
