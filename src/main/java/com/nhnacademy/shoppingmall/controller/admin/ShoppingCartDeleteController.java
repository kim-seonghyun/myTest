package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping.Method;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.shoppingCart.repository.impl.ShoppingCartRepositoryImpl;
import com.nhnacademy.shoppingmall.shoppingCart.service.ShoppingCartService;
import com.nhnacademy.shoppingmall.shoppingCart.service.impl.ShoppingCartServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequestMapping(method = Method.GET, value = "/deleteShoppingCart.do")
public class ShoppingCartDeleteController implements BaseController {

    ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl(new ShoppingCartRepositoryImpl());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        int productId = Integer.parseInt(req.getParameter("productId"));
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String userId = user.getUserId();
        if (Objects.isNull(user)) {
            return "redirect:/index.do";
        }
        shoppingCartService.delete(userId, productId);
        return "redirect:/shoppingCart.do";
    }
}
