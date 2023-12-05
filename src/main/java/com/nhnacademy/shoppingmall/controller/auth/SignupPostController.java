package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.domain.User.Auth;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RequestMapping(method = RequestMapping.Method.POST, value = "/signupAction.do")
@Slf4j
public class SignupPostController implements BaseController {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("user_id");
        String name = req.getParameter("user_name");
        String password = req.getParameter("user_password");
        String userBirth = req.getParameter("user_birth");

        try {
            User user = new User(id, name, password, userBirth, Auth.ROLE_USER, 1_000_000, LocalDateTime.now(), null);
            log.debug(user.toString());
            userService.saveUser(user);
            return "shop/main/index";
        } catch (UserAlreadyExistsException e) {
            log.debug("유저가 이미 존재함 !");
            return "redirect:/signup.do";
        } catch (RuntimeException e) {
            log.debug("런타임 에러 발생 !");
            return "redirect:/signup.do";
        }
    }
}
