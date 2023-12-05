package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping.Method;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequestMapping(method = Method.GET, value = "/mypage.do")
public class MypageController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (Objects.nonNull(session) && Objects.nonNull(session.getAttribute("user"))) {
            return "shop/main/my_page";
        } else {
            return "redirect:/index.do";
        }
    }
}
