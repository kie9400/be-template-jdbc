package com.springboot.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Setter
public class Cart {
    @Id
    private long cartId;
    private long memberId;
    private List<CartItem> cratItems;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
}
