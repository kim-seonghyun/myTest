package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.domain.User.Auth;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.UsersAddressService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import com.nhnacademy.shoppingmall.user.service.impl.UsersAddressServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@RequestMapping(method = RequestMapping.Method.POST, value = "/loginAction.do")
@Slf4j
public class LoginPostController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private final UsersAddressService usersAddressService = new UsersAddressServiceImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //todo#13-2 로그인 구현, session은 60분동안 유지됩니다.
        String id = req.getParameter("user_id");
        String pwd = req.getParameter("user_password");

        try {
            User user = userService.doLogin(id, pwd);
            user.setLatestLoginAt(LocalDateTime.now());
            userService.updateUser(user);
            HttpSession session = req.getSession();

            session.setMaxInactiveInterval(60 * 60);
            log.debug(user.getUserAuth().toString());
            session.setAttribute("user", user);
            if (user.getUserAuth().equals(Auth.ROLE_ADMIN)) {
                return "redirect:/admin/index.do";
            }

            List<Address> addresses = usersAddressService.findByUserId(user.getUserId());
            session.setAttribute("addresses", addresses);
            return "redirect:/index.do";
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        }
    }
}
