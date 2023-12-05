package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping.Method;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@RequestMapping(method = Method.POST, value = "/update.do")
@Slf4j
public class UpdatePostController implements BaseController {
    UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (Objects.isNull(user)) {
            return "redirect:/index.do";
        }
        String userName = req.getParameter("user_name");
        String userBirthday = req.getParameter("user_birth");
        String userPassword = req.getParameter("user_password");

        if (Objects.isNull(userName) || userName.isEmpty() || userName.length() > 50) {
            return "redirect:/update.do";
        }

        if (Objects.isNull(userPassword) || userPassword.isEmpty() || userPassword.length() > 200) {
            return "redirect:/update.do";
        }
        user.setUserName(userName);
        user.setUserBirth(userBirthday);
        user.setUserPassword(userPassword);
        userService.updateUser(user);
        return "shop/main/my_page";
    }
}
