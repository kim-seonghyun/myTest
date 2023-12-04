package com.nhnacademy.shoppingmall.address.repository;

import com.nhnacademy.shoppingmall.address.domain.Address;
import java.sql.Timestamp;
import java.util.Optional;

public interface AddressRepository {
    Optional<Address> findById(String address_id);

    int save(Address address);

    int deleteByAddressId(String address_id);

    int update(Address address);

    int updateModifiedDate(String address_id, Timestamp modified_date);

    int countByAddressId(String address_id);
}
