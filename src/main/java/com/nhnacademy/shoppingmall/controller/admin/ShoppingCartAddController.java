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

/**
 * User의 id를 cart ID로 사용.
 * Parameter로 들어온 productID 사용.
 */
@RequestMapping(value = "/addShoppingCart.do", method = Method.GET)
public class ShoppingCartAddController implements BaseController {
    ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl(new ShoppingCartRepositoryImpl());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (Objects.isNull(session) || Objects.isNull(session.getAttribute("user"))) {
            throw new RuntimeException("로그인 후 사용하세요.");
        }
        User user = (User) session.getAttribute("user");
        String userId = user.getUserId();
        int productId = Integer.parseInt(req.getParameter("productId"));
        shoppingCartService.save(userId, productId);

        return "redirect:/index.do";
    }
}
