package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.Products.domain.Products;
import com.nhnacademy.shoppingmall.Products.repository.ProductsRepository;
import com.nhnacademy.shoppingmall.Products.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.categories.domain.Categories;
import com.nhnacademy.shoppingmall.categories.repository.CategoriesRepository;
import com.nhnacademy.shoppingmall.categories.repository.impl.CategoriesRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping.Method;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * product랑 category 넘겨줌.
 *
 */
@RequestMapping(method = Method.GET, value = "/admin/updateProduct.do")
@Slf4j
public class ProductUpdateController implements BaseController {
    ProductsRepository productsRepository = new ProductRepositoryImpl();
    CategoriesRepository categoriesRepository = new CategoriesRepositoryImpl();
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int productId = Integer.parseInt(req.getParameter("productId"));

        Products products = productsRepository.findByProductId(productId).get();
        List<Categories> categoriesList = categoriesRepository.findAll();

        req.setAttribute("products", products);
        req.setAttribute("categories", categoriesList);
        return "/shop/for_admin/product_update";
    }
}
