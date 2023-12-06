package com.nhnacademy.shoppingmall.shoppingCart.repository;

import com.nhnacademy.shoppingmall.shoppingCart.domain.ShoppingCart;

public interface ShoppingCartRepository {
    int save(ShoppingCart shoppingCart);

    int countProductByCartIdAndProductId(String cartId, int productId);

    int deleteProductByProductId(String cartId, int productId);

}
