package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping.Method;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RequestMapping(method = Method.GET, value = "/addressUpdate.do")
@Slf4j
public class UpdateAddressController implements BaseController {
    AddressRepository addressRepository = new AddressRepositoryImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String addressId = req.getParameter("address_id");
        log.debug(addressId);
        if(Objects.isNull(addressId)){
            return "redirect:/mypage.do";
        }


        Optional<Address> address = addressRepository.findById(addressId);
        if (Objects.isNull(address)) {
            return "redirect:/mypage.do";
        }

        req.setAttribute("address", address.get());
        return "/shop/main/address_page";

    }
}
