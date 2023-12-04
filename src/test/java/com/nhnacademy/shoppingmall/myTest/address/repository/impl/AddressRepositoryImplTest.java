package com.nhnacademy.shoppingmall.myTest.address.repository.impl;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.util.DbUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class AddressRepositoryImplTest {
    AddressRepository addressRepository = new AddressRepositoryImpl();

    Address testAddress;

    @BeforeEach
    void setUp() throws SQLException {
        DbConnectionThreadLocal.initialize();
        testAddress = new Address("myAddress", "nhnacademy-test-address", "nhn아카데미", "donggu", "jeonrado", "61221");
        addressRepository.save(testAddress);
    }

    @AfterEach
    void tearDown() throws SQLException {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("id로 주소찾기")
    void findById() {
        Optional<Address> addressOptional = addressRepository.findById(testAddress.getAddress_id());
        Assertions.assertTrue(testAddress.equals(addressOptional.get()));
    }

    @Test
    @Order(2)
    void save() {
        Address newAddress = new Address("myAddress2", "nhnacademy-test-address2", "nhn아카데미2", "donggu", "jeonrado",
                "61221");
        int result = addressRepository.save(newAddress);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertTrue(
                        newAddress.equals(addressRepository.findById(newAddress.getAddress_id()).get()))
        );
    }

    @Test
    @Order(3)
    @DisplayName("address 삭제")
    void deleteByAddressId() {
        int result = addressRepository.deleteByAddressId(testAddress.getAddress_id());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertFalse(addressRepository.findById(testAddress.getAddress_id()).isPresent())
        );
    }

    @Test
    @Order(4)
    void update() {
        testAddress.setAddress_line1("changedAddress");

        int result = addressRepository.update(testAddress);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(testAddress,
                        addressRepository.findById(testAddress.getAddress_id()).get())
        );
    }

    @Test
    @Order(5)
    void updateModifiedDate() {
        int result = addressRepository.updateModifiedDate(testAddress.getAddress_id(),
                Timestamp.valueOf(LocalDateTime.now()));
        Assertions.assertEquals(1, result);
    }

    @Test
    @Order(6)
    void save_duplicate_address_id() {
        Throwable throwable = Assertions.assertThrows(RuntimeException.class, ()->{
            addressRepository.save(testAddress);
        });
        Assertions.assertTrue(
                throwable.getMessage().contains(SQLIntegrityConstraintViolationException.class.getName()));
        log.debug("errorMessage:{}", throwable.getMessage());

    }
}
