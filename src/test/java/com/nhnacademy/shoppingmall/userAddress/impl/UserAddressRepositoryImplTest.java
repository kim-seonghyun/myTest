package com.nhnacademy.shoppingmall.userAddress.impl;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.domain.UsersAddress;
import com.nhnacademy.shoppingmall.user.repository.UserAddressRepository;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserAddressRepositoryImpl;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserAddressRepositoryImplTest {
    UserAddressRepository userAddressRepository = new UserAddressRepositoryImpl();
    UserRepository userRepository = new UserRepositoryImpl();
    AddressRepository addressRepository = new AddressRepositoryImpl();
    UsersAddress testUsersAddress;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        User user = new User("myUser", "nhn아카데미", "nhnacademy-test-password", "19900505", User.Auth.ROLE_USER, 100_0000,
                LocalDateTime.now(), null);
        userRepository.save(user);

        Address address = new Address("myAdress", "nhnacademy-test-address", "nhn아카데미", "donggu", "jeonrado", "61221");
        addressRepository.save(address);
        testUsersAddress = new UsersAddress("myUser", "myAdress");
        userAddressRepository.save(testUsersAddress);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    void save() {
        User user2 = new User("myUser2", "nhn아카데미", "nhnacademy-test-password", "19900505", User.Auth.ROLE_USER,
                100_0000,
                LocalDateTime.now(), null);
        userRepository.save(user2);

        Address address2 = new Address("myAddress2", "nhnacademy-test-address", "nhn아카데미", "donggu", "jeonrado",
                "61221");
        addressRepository.save(address2);

        UsersAddress usersAddress2 = new UsersAddress("myUser2", "myAddress2");
        int result = userAddressRepository.save(usersAddress2);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertTrue(usersAddress2.equals(userAddressRepository.findUsersAdressByUsersIdAndAddressId(
                        usersAddress2.getUserId(), usersAddress2.getAddressId()).get()))
                );

    }

    @Test
    void deleteByUserIdAndAddressId() {
        int result = userAddressRepository.deleteByUserIdAndAddressId(testUsersAddress.getUserId(),
                testUsersAddress.getAddressId());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertFalse(
                        userAddressRepository.findUsersAdressByUsersIdAndAddressId(testUsersAddress.getUserId(),
                                testUsersAddress.getAddressId()).isPresent())
        );

    }

    @Test
    void findbyid() {
        Optional<UsersAddress> usersAddress = userAddressRepository.findUsersAdressByUsersIdAndAddressId(testUsersAddress.getUserId(),
                testUsersAddress.getAddressId());
        Assertions.assertTrue(testUsersAddress.equals(usersAddress.get()));
    }

}