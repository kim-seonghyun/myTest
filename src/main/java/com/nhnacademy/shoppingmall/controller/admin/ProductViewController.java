package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.Products.domain.Products;
import com.nhnacademy.shoppingmall.Products.repository.ProductsRepository;
import com.nhnacademy.shoppingmall.Products.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping.Method;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.view.ViewResolver;
import java.io.File;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = Method.GET, value = "/admin/productList.do")
public class ProductViewController implements BaseController {
    ProductsRepository productsRepository = new ProductRepositoryImpl();
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        List<Products> productsList = productsRepository.findAll();
        productsList.forEach(products -> products.setProductImage(ViewResolver.UPLOAD_DIR + File.separator + products.getProductImage()));
        productsList.forEach(products -> log.debug(products.getProductImage()));
        req.setAttribute("productsList", productsList);
        return "/shop/for_admin/product_view";
    }
}
