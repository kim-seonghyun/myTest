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
        testCategories =  categoriesRepository.save("shoes").get();
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
        Optional<Categories> optionalCategories = categoriesRepository.save(categoryName);
        Assertions.assertAll(
                () -> Assertions.assertEquals(optionalCategories.get(), categoriesRepository.findByCategoriesId(
                        optionalCategories.get().getCategoryID()).get())
        );
    }

    @Test
    @Order(3)
    void update() {
        testCategories.setCategoryName("신발");
        int result = categoriesRepository.update(testCategories);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(testCategories, categoriesRepository.findByCategoriesId(
                        testCategories.getCategoryID()).get())
        );
    }

    @Test
    @Order(4)
    void deleteByCategoriesId() {
        int result = categoriesRepository.deleteByCategoriesId(testCategories.getCategoryID());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertFalse(
                        categoriesRepository.findByCategoriesId(testCategories.getCategoryID()).isPresent())
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
    void findByCategoriesId() {
        Optional<Categories> categoriesOptional = categoriesRepository.findByCategoriesId(testCategories.getCategoryID());
        Assertions.assertEquals(testCategories, categoriesOptional.get());
    }
}