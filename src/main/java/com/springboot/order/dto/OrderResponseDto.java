package com.springboot.order.dto;

import com.springboot.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponseDto {
    private long orderId;
    private long memberId;
    private Order.OrderStatus orderStatus;
    // 아래는 주문 상품에 대한 새로운 DTO, coffeeId, 상품명, 가격 몇개 시켰는지 포함
    private List<OrderCoffeeResponseDto> orderCoffees;
    private LocalDateTime createdAt;
}
