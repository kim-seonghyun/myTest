package com.nhnacademy.shoppingmall.categories.repository;

import com.nhnacademy.shoppingmall.categories.domain.Categories;
import java.util.Optional;

public interface CategoriesRepository {
    Optional<Categories> save(String categoryName);

    int update(Categories categories);

    int deleteByCategoriesId(int categoryId);

    int countByCategoriesName(String categoryName);

    Optional<Categories> findByCategoriesId(int categoryId);
}
