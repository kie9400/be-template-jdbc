package com.springboot.example;

import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CartMapper {
    //Cart cartResponseDtoToCart(CartResponseDto cartResponseDto);
    default Cart cartResponseDtoToCart(CartResponseDto cartResponseDto){
        Cart cart = new Cart();
        cart.setMemberId(cartResponseDto.getMemberId());
        List<CartItem> cartItems = cartResponseDto.getCartItems().stream()
                .map(cartItemResponseDto ->
                        CartItem.builder()
                                .itemName(cartItemResponseDto.getItemName())
                                .quantity(cartItemResponseDto.getQuantity())
                                .price(cartItemResponseDto.getPrice())
                                .build())
                .collect(Collectors.toList());
        cart.setCratItems(cartItems);
        return cart;
    }
    //CartItem cartItemResponseDtoToCartItem(CartItemResponseDto cartItemResponseDto);
    //CartResponseDto cartToCartResponseDto(Cart cart)
    default CartResponseDto cartToCartResponseDto(Cart cart){
        CartResponseDto cartResponseDto = new CartResponseDto();
        List<CartItemResponseDto> cartItemResponse = cart.getCratItems().stream()
                .map(cartItem -> cartItemToCartItemResponseDto(cartItem))
                .collect(Collectors.toList());

        cartResponseDto.setCartId(cart.getCartId());
        cartResponseDto.setMemberId(cart.getMemberId());
        cartResponseDto.setCartItems(cartItemResponse);
        cartResponseDto.setCreateAt(cart.getCreateAt());
        cartResponseDto.setModifiedAt(cart.getModifiedAt());

        int totalprice = cartItemResponse.stream()
                .mapToInt(cartItemResponseDto->cartItemResponseDto.getQuantity() * cartItemResponseDto.getPrice())
                .sum();
        int totalquantity = cartItemResponse.stream()
                .mapToInt(cartItemResponseDto -> cartItemResponseDto.getQuantity())
                .sum();

        cartResponseDto.setTotalPrice(totalprice);
        cartResponseDto.setTotalQuantity(totalquantity);

        return cartResponseDto;
    }

    default CartItemResponseDto cartItemToCartItemResponseDto(CartItem cartItem){
        CartItemResponseDto responseDto = new CartItemResponseDto(
                cartItem.getCartItemId(),
                cartItem.getItemName(),
                cartItem.getQuantity(),
                cartItem.getPrice()
        );
        return responseDto;
    }
}
