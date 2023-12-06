package com.nhnacademy.shoppingmall.products.repository.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.shoppingmall.products.domain.Products;
import com.nhnacademy.shoppingmall.products.repository.ProductsRepository;
import com.nhnacademy.shoppingmall.categories.domain.Categories;
import com.nhnacademy.shoppingmall.categories.repository.CategoriesRepository;
import com.nhnacademy.shoppingmall.categories.repository.impl.CategoriesRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.Optional;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductRepositoryImplTest {
    ProductsRepository productsRepository = new ProductRepositoryImpl();
    CategoriesRepository categoriesRepository = new CategoriesRepositoryImpl();

    Products testProduct;

    Categories categories;

    @BeforeEach
    void setUp() throws SQLException {
        DbConnectionThreadLocal.initialize();
        categories = new Categories("category1");
        categoriesRepository.save(categories);
        categories = categoriesRepository.findByCategoriesName("category1").get();
        testProduct = new Products( categories.getCategoryID(), "model1", "name1", "image1", 100, "description1");
        productsRepository.save(testProduct);
        testProduct = productsRepository.findByModelNumber(testProduct.getModelNumber()).get();
    }

    @AfterEach
    void tearDown() throws SQLException {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("product 등록")
    void save() {
        Categories categories2 = new Categories("new Cate");
        categoriesRepository.save(categories2);
        categories2 = categoriesRepository.findByCategoriesName("new Cate").get();
        Products newProduct = new Products(categories2.getCategoryID(),  "model2", "name2", "image2", 200, "description2");
        int result = productsRepository.save(newProduct);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(newProduct, productsRepository.findByModelNumber(newProduct.getModelNumber()).get())
        );
    }

    @Test
    @Order(2)
    @DisplayName("product 삭제")
    void deleteByProductId() {
        int result = productsRepository.deleteByProductId(testProduct.getProductId());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertFalse(productsRepository.findByProductId(testProduct.getProductId()).isPresent())
        );
    }

    @Test
    @Order(3)
    @DisplayName("product 조회 by productId")
    void findByProductId() {
        Optional<Products> productOptional = productsRepository.findByProductId(testProduct.getProductId());
        assertEquals(testProduct, productOptional.get());
    }

    @Test
    @Order(4)
    @DisplayName("모든 product 조회")
    void findAll() {
        List<Products> products = productsRepository.findAll();
        Assertions.assertFalse(products.isEmpty());
    }

    @Test
    @Order(5)
    @DisplayName("product 수정")
    void update() {
        testProduct.setModelName("new model name");
        testProduct.setDescription("new description");

        int result = productsRepository.update(testProduct);
        assertAll(
                () -> assertEquals(1, result),
                () -> assertEquals(testProduct, productsRepository.findByProductId(testProduct.getProductId()).get())
        );
    }
}