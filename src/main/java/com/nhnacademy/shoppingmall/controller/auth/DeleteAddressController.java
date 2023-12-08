package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.address.domain.Address;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping.Method;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.service.UsersAddressService;
import com.nhnacademy.shoppingmall.user.service.impl.UsersAddressServiceImpl;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequestMapping(method = Method.GET, value = "/addressDelete.do")
public class DeleteAddressController implements BaseController {
    UsersAddressService usersAddressService = new UsersAddressServiceImpl();
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        if (Objects.isNull(user)) {
            throw new UserNotFoundException("유저가 존재하지 않습니다.");
        }
        String userId = user.getUserId();
        String address_id = req.getParameter("address_id");

        try {
            usersAddressService.deleteByUserIdAndAddressId(userId, address_id);
            List<Address> addressList = usersAddressService.findByUserId(user.getUserId());
            session.setAttribute("addresses", addressList);
            return "redirect:/mypage.do";
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
