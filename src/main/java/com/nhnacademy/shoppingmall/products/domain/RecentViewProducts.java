package com.nhnacademy.shoppingmall.products.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 최근 본 상품을 관리하는 클래스이다.
 */
public class RecentViewProducts {
    List<Products> recentViewProducts;
    private final int MAX_LIST_SIZE = 5;

    public RecentViewProducts(List<Products> recentViewProducts) {
        this.recentViewProducts = recentViewProducts;
    }

    public List<Products> getRecentViewProducts() {
        return recentViewProducts;
    }

    /**
     * 최근 본 상품을 추가한다.
     * @param products null 이 아닌 Products타입의 인스턴스를 입력해야한다.
     */
    public void add(Products products){
        for (Products products1 : recentViewProducts) {
            if (products1.getProductId() == products.getProductId()) {
                // 중복 발생.
                return;
            }
        }

        if (recentViewProducts.size() >= MAX_LIST_SIZE) {
            recentViewProducts.remove(0);
        }
        recentViewProducts.add(products);
    }
}
