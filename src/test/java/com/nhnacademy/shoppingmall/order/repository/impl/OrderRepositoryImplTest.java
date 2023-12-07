package com.nhnacademy.shoppingmall.order.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.order.domain.Orders;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class OrderRepositoryImplTest {
    UserRepository userRepository = new UserRepositoryImpl();
    OrderRepository orderRepository = new OrderRepositoryImpl(userRepository);

    User testUser;

    Orders testOrder;
    int orderId;
    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        testUser = new User("nhnacademy-test-user", "nhn아카데미", "nhnacademy-test-password", "19900505",
                User.Auth.ROLE_USER, 100_0000,
                LocalDateTime.now(), null);
        userRepository.save(testUser);
        testOrder = new Orders(testUser.getUserId(), LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        orderId = orderRepository.save(testOrder);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    void save() {
        User testUser2 = new User("nhnacademy-test-user2", "nhn아카데미2", "nhnacademy-test-password", "19900505",
                User.Auth.ROLE_USER, 100_0000,
                LocalDateTime.now(), null);
        userRepository.save(testUser2);
        Orders newOrders = new Orders(testUser2.getUserId(), LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        int result = orderRepository.save(newOrders);
        Assertions.assertTrue(result > 0);
    }

    @Test
    void findByOrderId() {
        Assertions.assertEquals(testOrder.getUserId() , orderRepository.findByOrderId(orderId).get().getUserId());
    }

    @Test
    void update() {
        testOrder.setShipDate(LocalDateTime.of(2022, 1, 31,2,3,4));
        orderRepository.update(testOrder);
        Assertions.assertEquals(testOrder.getShipDate(), orderRepository.findByOrderId(orderId).get().getShipDate());
    }

    @Test
    void delete() {
        int result = orderRepository.delete(orderId);
        Assertions.assertEquals(1, result);
    }
}