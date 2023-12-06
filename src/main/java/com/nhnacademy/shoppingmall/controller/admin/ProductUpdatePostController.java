package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.products.domain.Products;
import com.nhnacademy.shoppingmall.products.repository.ProductsRepository;
import com.nhnacademy.shoppingmall.products.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping.Method;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(method = Method.POST, value = "/admin/productUpdate.do")
public class ProductUpdatePostController implements BaseController {
    ProductsRepository productsRepository = new ProductRepositoryImpl();
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int productId = Integer.parseInt(req.getParameter("productId"));
        Products products=  productsRepository.findByProductId(productId).get();
        int categoryId =  Integer.parseInt(req.getParameter("categoryId"));
        String modelName = req.getParameter("modelName");
        String modelNumber = req.getParameter("modelNumber");
        int unitCost = Integer.parseInt(req.getParameter("unitCost"));
        String description = req.getParameter("description");

        products.setCategoryId(categoryId);
        products.setModelName(modelName);
        products.setModelNumber(modelNumber);
        products.setUnitCost(unitCost);
        products.setDescription(description);
        productsRepository.update(products);
        return "redirect:/admin/productList.do";
    }
}
