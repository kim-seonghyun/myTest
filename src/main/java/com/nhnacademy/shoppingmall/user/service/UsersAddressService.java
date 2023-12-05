package com.nhnacademy.shoppingmall.user.service;

import com.nhnacademy.shoppingmall.address.domain.Address;
import java.util.List;

public interface UsersAddressService {
    public List<Address> findByUserId(String userId);

    public int deleteByUserIdAndAddressId(String userId, String addressId);

}
