package com.nhnacademy.shoppingmall.common.page.repository.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.products.domain.Products;
import com.nhnacademy.shoppingmall.products.repository.ProductsRepository;
import com.nhnacademy.shoppingmall.products.repository.impl.ProductRepositoryImpl;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PageRepositoryImplTest {
    PageRepositoryImpl pageRepository = new PageRepositoryImpl();
    ProductsRepository productsRepository = new ProductRepositoryImpl();
    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();

        final int CATEGORY_ID =560 ;
        while (productsRepository.findProductAll().size() < 110) {
            productsRepository.save(
                    new Products(CATEGORY_ID, "model", "modeLName", "productImage", 1000, "description"));
        }

    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    void productTotalPage() {
        int productCount = pageRepository.productTotalPage(10);
        Assertions.assertEquals(11, productCount);
    }

    @Test
    void getProductContents() {
        List<Products> productsList = pageRepository.getProductContents(1, 10);
        Assertions.assertEquals(10, productsList.size());
        
    }
}