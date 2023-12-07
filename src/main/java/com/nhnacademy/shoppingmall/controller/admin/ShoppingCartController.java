package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping.Method;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.products.domain.Products;
import com.nhnacademy.shoppingmall.products.repository.ProductsRepository;
import com.nhnacademy.shoppingmall.products.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.shoppingCart.domain.ShoppingCart;
import com.nhnacademy.shoppingmall.shoppingCart.repository.impl.ShoppingCartRepositoryImpl;
import com.nhnacademy.shoppingmall.shoppingCart.service.ShoppingCartService;
import com.nhnacademy.shoppingmall.shoppingCart.service.impl.ShoppingCartServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@RequestMapping(value = "/shoppingCart.do", method = Method.GET)
@Slf4j
public class ShoppingCartController implements BaseController {
    ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl(new ShoppingCartRepositoryImpl());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        if (Objects.isNull(session) && Objects.isNull(session.getAttribute("user"))) {
            throw new RuntimeException("로그인 후에 장바구니를 사용해 주세요");
        }
        User user = (User) session.getAttribute("user");
        List<Products> productsList=  shoppingCartService.findAllProductsByCartId(user.getUserId());
        productsList.forEach(products -> log.debug(products.getDescription()));
        List<ShoppingCart> shoppingCartList = shoppingCartService.findAllShoppingCartByCartId(user.getUserId());
        shoppingCartList.forEach(shoppingCart -> log.debug(String.valueOf(shoppingCart.getProductId())));
        req.setAttribute("productList", productsList);
        req.setAttribute("cartList",shoppingCartList);
        return "shop/main/shopping_cart";
    }
}
