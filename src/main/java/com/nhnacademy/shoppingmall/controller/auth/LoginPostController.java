package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@RequestMapping(method = RequestMapping.Method.POST, value = "/loginAction.do")
@Slf4j
public class LoginPostController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //todo#13-2 로그인 구현, session은 60분동안 유지됩니다.
        String id = req.getParameter("user_id");
        String pwd = req.getParameter("user_password");

        try {
            User user = userService.doLogin(id, pwd);
            log.debug(user.toString());
            HttpSession session = req.getSession();
            session.setMaxInactiveInterval(60 * 60);
            session.setAttribute("user_id", user.getUserId());
            session.setAttribute("user_name", user.getUserName());
            return "shop/main/index";
        } catch (UserNotFoundException e) {
            return "redirect:/login.do";
        }
    }
}
