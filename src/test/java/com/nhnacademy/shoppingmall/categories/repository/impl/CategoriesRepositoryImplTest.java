package com.nhnacademy.shoppingmall.categories.repository.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.shoppingmall.categories.domain.Categories;
import com.nhnacademy.shoppingmall.categories.repository.CategoriesRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class CategoriesRepositoryImplTest {

    CategoriesRepository categoriesRepository = new CategoriesRepositoryImpl();
    Categories testCategories;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        testCategories = new Categories("shoes");
        categoriesRepository.save(testCategories);
        testCategories = categoriesRepository.findByCategoriesName("shoes").get();
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }


    @Test
    @Order(2)
    void save() {
        String categoryName = "outer";
        int result = categoriesRepository.save(new Categories(categoryName));
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(testCategories, categoriesRepository.findByCategoriesName(
                        testCategories.getCategoryName()).get())
        );
    }

    @Test
    @Order(3)
    void update() {
        testCategories.setCategoryName("신발");
        int result = categoriesRepository.update(testCategories);
        log.debug(testCategories.getCategoryName());
        log.debug(categoriesRepository.findByCategoryId(
                testCategories.getCategoryID()).get().getCategoryName());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),

                () -> Assertions.assertEquals(testCategories, categoriesRepository.findByCategoriesName(
                        testCategories.getCategoryName()).get())
        );
    }

    @Test
    @Order(4)
    void deleteByCategoriesName() {
        int result = categoriesRepository.deleteByCategoriesName(testCategories.getCategoryName());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertFalse(
                        categoriesRepository.findByCategoriesName(testCategories.getCategoryName()).isPresent())
        );
    }

    @Test
    @Order(5)
    void countByCategoriesName() {
        int result = categoriesRepository.countByCategoriesName(testCategories.getCategoryName());
        Assertions.assertEquals(1, result);
    }

    @Test
    @Order(1)
    void findByCategoriesName() {
        Optional<Categories> categoriesOptional = categoriesRepository.findByCategoriesName(testCategories.getCategoryName());
        Assertions.assertEquals(testCategories, categoriesOptional.get());
    }
}