package com.nhnacademy.shoppingmall.controller.index;

import com.nhnacademy.shoppingmall.products.domain.Products;
import com.nhnacademy.shoppingmall.products.repository.ProductsRepository;
import com.nhnacademy.shoppingmall.products.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;

import com.nhnacademy.shoppingmall.common.mvc.view.ViewResolver;
import java.io.File;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET,value = {"/index.do"})
public class IndexController implements BaseController {
    ProductsRepository productsRepository = new ProductRepositoryImpl();
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        List<Products> productsList = productsRepository.findAll();
        productsList.forEach(products -> products.setProductImage(ViewResolver.UPLOAD_DIR + File.separator + products.getProductImage()));
        req.setAttribute("productsList", productsList);
        return "shop/main/index";
    }
}