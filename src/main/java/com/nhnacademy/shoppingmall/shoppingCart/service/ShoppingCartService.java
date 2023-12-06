package com.nhnacademy.shoppingmall.shoppingCart.service;

import com.nhnacademy.shoppingmall.products.domain.Products;
import com.nhnacademy.shoppingmall.shoppingCart.domain.ShoppingCart;
import java.util.List;

public interface ShoppingCartService {
    List<Products> findAllProductsByCartId(String cartId);

    // 유저가 상품 하나를 index페이지에서 추가하는경우
    int save(String cartId, int productId);

    int delete(String cartId, int productId);

    // 개수 조절.
    int updateQuentity(ShoppingCart shoppingCartcs);


}
