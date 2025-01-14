package com.springboot.example;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
public class CartItem {
    @Id
    private long cartItemId;
    private String itemName;
    private int quantity;
    private int price;
}
