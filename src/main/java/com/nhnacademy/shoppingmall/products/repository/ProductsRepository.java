package com.nhnacademy.shoppingmall.products.repository;

import com.nhnacademy.shoppingmall.products.domain.Products;
import java.util.List;
import java.util.Optional;

public interface ProductsRepository {

    List<Products> findProductAll();
    // (카테고리 포합) 등록,수정, 삭제
    int save(Products products);

    int deleteByProductId(int productId);
    Optional<Products> findByProductId(int productId);
    List<Products> findAll();

    int deleteByModelNumber(String modelNumber, String categoryName);

    int update(Products products);

    Optional<Products> findByModelNumber(String modelNumber);

    List<Products> findProductsByCategoryId(int categoryId);

}
