package com.nhnacademy.shoppingmall.products.domain;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecentViewProductsTest {

    @Test
    void add() {
        RecentViewProducts recentViewProducts = new RecentViewProducts(new ArrayList<>());
        for (int i = 0; i < 5; i++) {
            recentViewProducts.add(new Products(1, 1, "1", "modelname", "image", 100, "description"));
        }
        recentViewProducts.add(new Products(2, 1, "1", "modelname", "image", 100, "description"));
        Assertions.assertEquals(recentViewProducts.getRecentViewProducts().get(4).getProductId(), 2);
        Assertions.assertEquals(5, recentViewProducts.getRecentViewProducts().size());
    }

    @Test
    void duplicate_test(){
        RecentViewProducts recentViewProducts = new RecentViewProducts(new ArrayList<>());
        recentViewProducts.add(new Products(1, 1, "1", "modelname", "image", 100, "description"));
        recentViewProducts.add(new Products(1, 1, "1", "modelname", "image", 100, "description"));

        Assertions.assertEquals(1, recentViewProducts.getRecentViewProducts().size());
    }
}