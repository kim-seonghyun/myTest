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

@Slf4j
@RequestMapping(method = Method.POST, value = "/addressUpdate.do")
public class UpdateAddressPostController implements BaseController {
    AddressRepository addressRepository = new AddressRepositoryImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address_id = req.getParameter("address_id");
        String address_line1 = req.getParameter("address_line1");
        String address_line2 = req.getParameter("address_line2");
        String city = req.getParameter("city");
        String sido = req.getParameter("sido");
        String postal_code = req.getParameter("postal_code");
        Address address = null;
        try {
            Optional<Address> optionalAddress = addressRepository.findById(address_id);
            if (optionalAddress.isEmpty()) {
                return "redirect:/index.do";
            }
            address = optionalAddress.get();
        } catch (RuntimeException e) {
            return "redirect:/index.do";
        }

        address.setAddress_line1(address_line1);
        address.setAddress_line2(address_line2);
        address.setCity(city);
        address.setSido(sido);
        address.setPostal_code(postal_code);
        addressRepository.update(address);
        return "/shop/main/my_page";
    }
}
