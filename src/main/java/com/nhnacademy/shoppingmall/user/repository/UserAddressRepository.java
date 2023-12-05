package com.nhnacademy.shoppingmall.user.repository;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.user.domain.UsersAddress;
import java.util.List;
import java.util.Optional;

public interface UserAddressRepository {
    // 유저 - 주소 등록
    int save(UsersAddress usersAddress);

    int deleteByUserIdAndAddressId(String user_id, String address_id);

    // 특정 유저의 주소 리스트
    Optional<UsersAddress> findUsersAdressByUsersIdAndAddressId(String users_id, String address_id);


    int countByUserIdAndAddressId(String userId, String addressId);
}
