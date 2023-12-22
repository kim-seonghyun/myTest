package com.nhnacademy.shoppingmall.common.page.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.products.domain.Products;
import java.util.List;

public interface PageReopsitory {
    int productTotalPage(int pageSize);

    List<Products> getProductContents(int pageNumber, int pageSize);
}
