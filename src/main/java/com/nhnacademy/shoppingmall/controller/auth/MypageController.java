package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping.Method;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.UsersAddressService;
import com.nhnacademy.shoppingmall.user.service.impl.UsersAddressServiceImpl;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequestMapping(method = Method.GET, value = "/mypage.do")
public class MypageController implements BaseController {
    private final UsersAddressService usersAddressService = new UsersAddressServiceImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);

        if (Objects.nonNull(session) ) {
            User user = (User) session.getAttribute("user");
            List<Address> addresses = usersAddressService.findByUserId(user.getUserId());
            session.setAttribute("addresses", addresses);

            return "shop/main/my_page";
        } else {
            return "redirect:/index.do";
        }
    }
}
