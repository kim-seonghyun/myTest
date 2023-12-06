package com.nhnacademy.shoppingmall.controller.admin;

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
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = Method.GET, value = "/admin/productRegistration.do")
public class ProductRegistrationController implements BaseController {
    CategoriesRepository categoriesRepository = new CategoriesRepositoryImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (Objects.isNull(session)) {
            return "redirect:/index.do";
        } else {
            List<Categories> categoriesList =categoriesRepository.findAll();
            if(Objects.nonNull(categoriesList)){
                req.setAttribute("categories", categoriesList);
            }
            return "shop/for_admin/product_registration";
        }
    }
}
