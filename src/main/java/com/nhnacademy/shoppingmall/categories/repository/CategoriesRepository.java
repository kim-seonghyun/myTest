package com.nhnacademy.shoppingmall.categories.repository;

import com.nhnacademy.shoppingmall.categories.domain.Categories;
import java.util.Optional;

public interface CategoriesRepository {


    int save(Categories categories);

    int update(Categories categories);
    int updateByCategoriesID(Categories categoryId);


    int deleteByCategoriesId(int categoryId);
    int deleteByCategoriesName(String categoryName);

    int countByCategoriesName(String categoryName);

    Optional<Categories> findByCategoriesName(String categoryName);

    Optional<Categories> findByCategoryId(int categoryId);
}
