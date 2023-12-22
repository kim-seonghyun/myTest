package com.nhnacademy.shoppingmall.common.page.Service;

import com.nhnacademy.shoppingmall.products.domain.Products;
import java.util.List;


public interface PageService {
    int productToTalPage(int pagesize);

    List<Products> getProductContents(int number, int pagesize);
}
