package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping.Method;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.products.domain.Products;
import com.nhnacademy.shoppingmall.products.domain.RecentViewProducts;
import com.nhnacademy.shoppingmall.products.repository.ProductsRepository;
import com.nhnacademy.shoppingmall.products.repository.impl.ProductRepositoryImpl;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


/**
 * 상품 detail 정보를 반환한다.
 */
@Slf4j
@RequestMapping(method = Method.GET, value = {"/getProductDetail.do"})
public class ProductDetailController implements BaseController {
    ProductsRepository productsRepository = new ProductRepositoryImpl();
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // 최근 본 상품에 추가한다.
        int productId = Integer.parseInt(req.getParameter("productId"));
        if(Objects.isNull(req.getParameter("productId"))){
            throw new RuntimeException("productId를 찾지 못하였습니다.");
        }
        HttpSession session = req.getSession(false);
        if (Objects.nonNull(session.getAttribute("recentViewProducts")) ) {
            RecentViewProducts recentViewProducts = (RecentViewProducts) session.getAttribute("recentViewProducts");
            Optional<Products> recentChooseProduct = productsRepository.findByProductId(productId);
            if (recentChooseProduct.isPresent()) {
                recentViewProducts.add(recentChooseProduct.get());
                session.setAttribute("recentViewProducts", recentViewProducts);
            }
        }

        // detail 페이지 구현 전까지는 index.
        return "redirect:/index.do";
    }
}
