package com.nhnacademy.shoppingmall.controller.index;

import static com.nhnacademy.shoppingmall.common.mvc.view.ViewResolver.getImageDir;

import com.nhnacademy.shoppingmall.categories.domain.Categories;
import com.nhnacademy.shoppingmall.categories.repository.CategoriesRepository;
import com.nhnacademy.shoppingmall.categories.repository.impl.CategoriesRepositoryImpl;
import com.nhnacademy.shoppingmall.common.page.Service.PageService;
import com.nhnacademy.shoppingmall.common.page.Service.impl.PageServiceImpl;
import com.nhnacademy.shoppingmall.common.page.repository.impl.PageRepositoryImpl;
import com.nhnacademy.shoppingmall.products.domain.Products;
import com.nhnacademy.shoppingmall.products.repository.ProductsRepository;
import com.nhnacademy.shoppingmall.products.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;

import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RequestMapping(method = RequestMapping.Method.GET,value = {"/index.do"})
@Slf4j
public class IndexController implements BaseController {
    private PageService pageService = new PageServiceImpl(new PageRepositoryImpl());
    private CategoriesRepository categoriesRepository = new CategoriesRepositoryImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        final int PAGE_SIZE = 8;
        String categoryId = req.getParameter("categoryId");
        log.debug("categoryId={}", categoryId);

        int currentPage = (req.getParameter("page") == null) ? 1 : Integer.parseInt(req.getParameter("page"));
        List<Products> productListPage = null;
        int totalPageSize = 0;
        if (Objects.isNull(categoryId)) {
            totalPageSize = pageService.productToTalPage(PAGE_SIZE);
            productListPage = pageService.getProductContents(currentPage, PAGE_SIZE);
            log.debug(String.valueOf(currentPage));
        }else{
            totalPageSize = pageService.productTotalPage(PAGE_SIZE, Integer.parseInt(categoryId));
             productListPage = pageService.getProductContents(currentPage, PAGE_SIZE,
                     Integer.parseInt(categoryId));
        }
        productListPage.forEach(products -> products.setProductImage(getImageDir(products.getProductImage())));
        req.setAttribute("productsList", productListPage);
        List<Categories> categoriesList = categoriesRepository.findAll();
        if(Objects.nonNull(categoriesList)){
            req.setAttribute("categories", categoriesList);
        }
        log.debug("totalPageSIze={}",totalPageSize);
        req.setAttribute("totalPageSize", totalPageSize);


        return "shop/main/index";
    }
}