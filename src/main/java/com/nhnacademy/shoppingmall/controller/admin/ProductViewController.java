package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.Products.domain.Products;
import com.nhnacademy.shoppingmall.Products.repository.ProductsRepository;
import com.nhnacademy.shoppingmall.Products.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping.Method;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = Method.GET, value = "/admin/productList.do")
public class ProductViewController implements BaseController {
    ProductsRepository productsRepository = new ProductRepositoryImpl();
    //    CategoriesRepository categoriesRepository = new CategoriesRepositoryImpl();
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        List<Products> productsList = productsRepository.findAll();
//        List<Categories> categoriesList = categoriesRepository.findAll();

        req.setAttribute("productsList", productsList);
        return "/shop/for_admin/product_view";
    }
}
