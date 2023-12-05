package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.repository.UserAddressRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserAddressRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UsersAddressService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UsersAddressServiceImpl implements UsersAddressService {

    AddressRepository addressRepository = new AddressRepositoryImpl();
    UserAddressRepository userAddressRepository = new UserAddressRepositoryImpl();


    @Override
    public List<Address> findByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select address_id from users_address where user_id = ?";
        List<Address> addresses = new ArrayList<>();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, userId);
            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {
                String addressId = rs.getString(1);
                if (addressRepository.findById(addressId).isPresent()) {
                    Address address = addressRepository.findById(addressId).get();
                    addresses.add(address);
                }
            }
            return addresses;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByUserIdAndAddressId(String userId, String addressId) {
        try {
            userAddressRepository.deleteByUserIdAndAddressId(userId, addressId);
        } catch (RuntimeException e) {
            return 0;
        }

        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if(optionalAddress.isEmpty()){
            return 0;
        }
        return addressRepository.deleteByAddressId(addressId);
    }
}
