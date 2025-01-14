package com.springboot.order.service;

import com.springboot.coffee.service.CoffeeService;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.service.MemberService;
import com.springboot.order.entity.Order;
import com.springboot.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//실제 존재하는 회원인지
//ORDER_COFFEE안에 실제 존재하는 커피인지
//ORDER SERVICE는 MEMBER랑 COFFEE에 대한 의존성이 있음
//다른 도메인의 액세스 계층에 접근X
//오더 서비스는 멤버/커피 서비스를 DI받으면된다.
//주문 삭제가 아니라 주문 취소
//주문 상태에 따라서 조건을 넣어야 ( 주문 요청일때만 취소가능 )

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private MemberService memberService;
    private CoffeeService coffeeService;

    public OrderService(OrderRepository orderRepository, MemberService memberService, CoffeeService coffeeService) {
        this.orderRepository = orderRepository;
        this.memberService = memberService;
        this.coffeeService = coffeeService;
    }

    public Order createOrder(Order order) {
        // TODO should business logic
        // 주문하려는 회원이 존재하는지 확인
        memberService.findVerifiedMember(order.getMemberId());

        //받아온 주문의 커피가 실제하는 커피인지 확인
        order.getOrderCoffees().stream()
                .forEach(coffee -> coffeeService.findVerifiedCoffeeId(coffee.getCoffeeId()));
        //coffeeService.findOrderedCoffees(order);

        // TODO order 객체는 나중에 DB에 저장 후, 되돌려 받는 것으로 변경 필요.
        return orderRepository.save(order);
    }

    public Order findOrder(long orderId) {
        // TODO should business logic
        return findVerifiedOrder(orderId);

        // TODO order 객체는 나중에 DB에서 조회 하는 것으로 변경 필요.
//        return new Order(1L, 1L);
    }

    // 주문 수정 메서드는 사용하지 않습니다.
    public List<Order> findOrders() {
        // TODO should business logic
        return (List<Order>) orderRepository.findAll();
        // TODO order 객체는 나중에 DB에서 조회하는 것으로 변경 필요.
//        return List.of(new Order(1L, 1L),
//                new Order(2L, 2L));
        //return null;
    }

    public void cancelOrder(long orderId) {
        // TODO should business logic
        Order findorder = findVerifiedOrder(orderId);


        //상태가 ORDER_REQUEST인것만 취소가 가능
        if(!findorder.getOrderStatus().getStepDescription().equals("주문 요청")){
            throw new BusinessLogicException(ExceptionCode.CANNOT_CHANGE_ORDER);
        }
        findorder.setOrderStatus(Order.OrderStatus.ORDER_CANCEL);
        orderRepository.save(findorder);
    }

    public Order findVerifiedOrder(long orderId){
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        Order order =optionalOrder.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));

        return order;
    }
}
