package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.exception.AddressAlreadyExistsException;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping.Method;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.domain.UsersAddress;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.exception.UsersAddressAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.repository.UserAddressRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserAddressRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UsersAddressService;
import com.nhnacademy.shoppingmall.user.service.impl.UsersAddressServiceImpl;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@RequestMapping(method = Method.POST, value = "/addressRegistration.do")
@Slf4j
public class AddressPostController implements BaseController {
    UsersAddressService usersAddressService = new UsersAddressServiceImpl();
    AddressRepository addressRepository = new AddressRepositoryImpl();
    UserAddressRepository userAddressRepository = new UserAddressRepositoryImpl();
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        if (Objects.isNull(session) || Objects.isNull(session.getAttribute("user"))) {
            throw new RuntimeException("로그인 후에 진행하세요.");
        }
        User user = (User) session.getAttribute("user");


        String addressId = req.getParameter("address_id");
        String addressLine1 = req.getParameter("address_line1");
        String addressLine2 = req.getParameter("address_line2");
        String city = req.getParameter("city");
        String sido = req.getParameter("sido");
        String postalCode = req.getParameter("postal_code");

        Address address = new Address(addressId, addressLine1, addressLine2, city, sido, postalCode);
        try {
            addressRepository.save(address);
        } catch (AddressAlreadyExistsException e) {
            log.debug(e.getMessage());
            throw new AddressAlreadyExistsException();
        }
        UsersAddress usersAddress = new UsersAddress( user.getUserId(),address.getAddress_id() );
        try {
            userAddressRepository.save(usersAddress);
        } catch (UsersAddressAlreadyExistsException e) {
            log.debug(e.getMessage());
            throw new UserAlreadyExistsException(user.getUserId());
        }
        // 이렇게 갱신시켜서 보내줘도 되나?
        session.setAttribute("addresses", usersAddressService.findByUserId(user.getUserId()));
        return "redirect:/mypage.do";
    }
}
