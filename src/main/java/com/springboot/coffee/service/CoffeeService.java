package com.springboot.coffee.service;

import com.springboot.coffee.entity.Coffee;
import com.springboot.coffee.repository.CoffeeRepository;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;

import com.springboot.member.entity.Member;
import com.springboot.order.entity.Order;
import com.springboot.order.entity.OrderCoffee;
import com.springboot.order.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoffeeService {
    private final CoffeeRepository coffeeRepository;
    public CoffeeService(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    public Coffee createCoffee(Coffee coffee) {
        String coffeeCode = coffee.getCoffeeCode().toUpperCase();
        verifyExistsCoffeeCode(coffeeCode);

        //객체를 바꾸고 저장해야 대문자로 저장된다.
        //toUpperCase는 원본데이터를 바꾸지않기 때문이다.
        coffee.setCoffeeCode(coffeeCode);
        return coffeeRepository.save(coffee);
    }

    public Coffee updateCoffee(Coffee coffee) {
        Coffee findCoffee =findVerifiedCoffeeId(coffee.getCoffeeId());

        Optional.ofNullable(coffee.getKorName())
                .ifPresent(korName -> findCoffee.setKorName(korName));

        Optional.ofNullable(coffee.getEngName())
                .ifPresent(engName -> findCoffee.setEngName(engName));

        Optional.ofNullable(coffee.getPrice())
                .ifPresent(price -> findCoffee.setPrice(price));

        return findCoffee;
    }

    public Coffee findCoffee(long coffeeId) {
        Coffee findCoffee = findVerifiedCoffeeId(coffeeId);
        return findCoffee;
    }

    public List<Coffee> findCoffees()
    {
        return (List<Coffee>)coffeeRepository.findAll();
    }

    public void deleteCoffee(long coffeeId) {
        Coffee findCoffee = findVerifiedCoffeeId(coffeeId);
        coffeeRepository.delete(findCoffee);
    }

    // 주문에 해당하는 커피 정보 조회
    public List<Coffee> findOrderedCoffees(Order order) {
//        List<Coffee> list = new ArrayList<>();
//        Set<OrderCoffee> orderCoffeeSet = order.getOrderCoffees();
//        for(OrderCoffee orderCoffee : orderCoffeeSet){
//            long currentCoffeeId = orderCoffee.getOrderCoffeeId();
//            Coffee currentCoffee = findCoffee(currentCoffeeId);
//            list.add(currentCoffee);
//        }
        //return list;

        return order.getOrderCoffees().stream()
                .map(orderCoffee -> findVerifiedCoffeeId(orderCoffee.getCoffeeId()))
                .collect(Collectors.toList());
    }

    private void verifyExistsCoffeeCode(String coffeeCode){
        Optional<Coffee> optionalCoffee = coffeeRepository.findByCoffeeCode(coffeeCode);

        if(optionalCoffee.isPresent()){
            //이미 커피코드가 존재할경우
            throw new BusinessLogicException(ExceptionCode.COFFEE_CODE_EXISTS);
        }
    }

    public Coffee findVerifiedCoffeeId(long coffeeId){
        Optional<Coffee> optionalCoffee = coffeeRepository.findByCoffee(coffeeId);

        Coffee findCoffee = optionalCoffee.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.COFFEE_NOT_FOUND));

        return findCoffee;
    }
}
