package com.nhnacademy.shoppingmall.shoppingCart.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.shoppingmall.categories.domain.Categories;
import com.nhnacademy.shoppingmall.categories.repository.CategoriesRepository;
import com.nhnacademy.shoppingmall.categories.repository.impl.CategoriesRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.products.domain.Products;
import com.nhnacademy.shoppingmall.products.repository.ProductsRepository;
import com.nhnacademy.shoppingmall.products.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.shoppingCart.domain.ShoppingCart;
import com.nhnacademy.shoppingmall.shoppingCart.repository.impl.ShoppingCartRepositoryImpl;
import com.nhnacademy.shoppingmall.shoppingCart.service.ShoppingCartService;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShoppingCartServiceImplTest {
    ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl(new ShoppingCartRepositoryImpl());
    ProductsRepository productsRepository = new ProductRepositoryImpl();
    CategoriesRepository categoriesRepository = new CategoriesRepositoryImpl();

    ShoppingCart shoppingCart;
    Products testProduct;
    Categories categories;
    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        categories = new Categories("category1");
        categoriesRepository.save(categories);
        categories = categoriesRepository.findByCategoriesName("category1").get();
        testProduct = new Products( categories.getCategoryID(), "model1", "name1", "image1", 100, "description1");
        productsRepository.save(testProduct);
        testProduct = productsRepository.findByModelNumber(testProduct.getModelNumber()).get();
        shoppingCart = new ShoppingCart("myCart", 3, testProduct.getProductId());
        shoppingCartService.save(shoppingCart.getCartId(), shoppingCart.getProductId());
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    void findAllProductsByCartId() {
        if (productsRepository.findByProductId(shoppingCart.getProductId()).isPresent()) {
            Products products = productsRepository.findByProductId(shoppingCart.getProductId()).get();
            List<Products> productsList =  shoppingCartService.findAllProductsByCartId(shoppingCart.getCartId());
            Assertions.assertEquals(products, productsList.get(0));
        }

    }

    @Test
    void updateQuentity() {
        shoppingCart.setQuantity(5);
        int result = shoppingCartService.updateQuentity(shoppingCart);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result)
        );
    }
}