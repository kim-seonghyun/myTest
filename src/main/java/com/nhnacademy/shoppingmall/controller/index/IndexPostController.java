package com.nhnacademy.shoppingmall.controller.index;

import static com.nhnacademy.shoppingmall.common.mvc.view.ViewResolver.getImageDir;

import com.nhnacademy.shoppingmall.categories.domain.Categories;
import com.nhnacademy.shoppingmall.categories.repository.CategoriesRepository;
import com.nhnacademy.shoppingmall.categories.repository.impl.CategoriesRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping.Method;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.products.domain.Products;
import com.nhnacademy.shoppingmall.products.repository.ProductsRepository;
import com.nhnacademy.shoppingmall.products.repository.impl.ProductRepositoryImpl;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RequestMapping(method = Method.POST,value = {"/index.do"})
@Slf4j
public class IndexPostController implements BaseController {
    ProductsRepository productsRepository = new ProductRepositoryImpl();
    CategoriesRepository categoriesRepository = new CategoriesRepositoryImpl();
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int categoryId = Integer.parseInt(req.getParameter("categoryId"));
        log.debug("카테곡리 아이디={}",categoryId);
        List<Products> productsList = productsRepository.findProductsByCategoryId(categoryId);
        productsList.forEach(products -> products.setProductImage(getImageDir(products.getProductImage())));
        req.setAttribute("productsList", productsList);
        List<Categories> categoriesList =categoriesRepository.findAll();
        if(Objects.nonNull(categoriesList)){
            req.setAttribute("categories", categoriesList);
        }
        return "shop/main/index";
    }
}