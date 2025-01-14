package com.springboot.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderPostDto {
    @Positive
    private long memberId;

    //Positive
    //객체 안에 객체까지 Valid를 적용하기 위해서는 @Valid를 적어주어야한다.
    @Valid
    private List<OrderCoffeeDto> orderCoffees;





    // {membmerId : 1,
    //   orderCoffees : [
    //      { coffeeId : 1,
    //       quantity : 10
    //       },
    //      { coffeeId : 2,
    //        quantity : 20
    //      }
    //    ]
    // }
}
