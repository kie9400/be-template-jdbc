package com.springboot.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Table("ORDERS")
public class Order {
    @Id
    private long orderId;
    private long memberId;

    //외래키 역할
    @MappedCollection(idColumn = "ORDER_ID")
    private Set<OrderCoffee> orderCoffees = new LinkedHashSet<>();

    private LocalDateTime createdAt = LocalDateTime.now();

    //상태는 일반적으로 상수로 관리한다.
    private OrderStatus orderStatus = OrderStatus.ORDER_REQUEST;

    public enum OrderStatus {
        ORDER_REQUEST(1, "주문 요청"),
        ORDER_CORDER_REQUESTNFIRM(2, "주문 확정"),
        ORDER_COMPLETE(3, "주문 완료"),
        ORDER_CANCEL(4, "주문 취소");

        @Getter
        private int stepNumber;
        @Getter
        private String stepDescription;

        OrderStatus(int stepNumber, String stepDescription) {
            this.stepNumber = stepNumber;
            this.stepDescription = stepDescription;
        }
    }
}
