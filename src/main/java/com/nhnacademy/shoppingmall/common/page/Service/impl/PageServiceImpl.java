package com.nhnacademy.shoppingmall.common.page.Service.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Service.PageService;
import com.nhnacademy.shoppingmall.common.page.repository.PageReopsitory;
import com.nhnacademy.shoppingmall.products.domain.Products;
import java.sql.Connection;
import java.util.List;

public class PageServiceImpl implements PageService {

    private PageReopsitory pageReopsitory;

    public PageServiceImpl(PageReopsitory pageReopsitory) {
         DbConnectionThreadLocal.getConnection();
        this.pageReopsitory = pageReopsitory;
    }

    @Override
    public int productToTalPage(int pagesize) {
        return pageReopsitory.productTotalPage(pagesize);
    }

    @Override
    public int productTotalPage(int pagesize, int categoryId) {
        return pageReopsitory.productTotalPage(pagesize, categoryId);
    }

    @Override
    public List<Products> getProductContents(int number, int pagesize) {
        return pageReopsitory.getProductContents(number, pagesize);
    }

    @Override
    public List<Products> getProductContents(int number, int pagesize, int categoryId) {
        return pageReopsitory.getProductContents(number, pagesize, categoryId);
    }
}
