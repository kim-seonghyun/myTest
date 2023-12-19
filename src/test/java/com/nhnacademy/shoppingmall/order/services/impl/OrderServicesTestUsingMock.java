package com.nhnacademy.shoppingmall.order.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.nhnacademy.shoppingmall.order.domain.Orders;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.order.services.OrderServices;
import com.nhnacademy.shoppingmall.orderdetail.domain.OrderDetails;
import com.nhnacademy.shoppingmall.orderdetail.repository.OrderDetailRepository;
import com.nhnacademy.shoppingmall.shoppingCart.service.ShoppingCartService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.UserService;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)

class OrderServicesTestUsingMock {
    OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
//    OrderDetailRepository orderDetailRepository = Mockito.mock(OrderDetailRepository.class);
//    ShoppingCartService shoppingCartService = Mockito.mock(ShoppingCartService.class);
//
//    UserService userService = Mockito.mock(UserService.class);
    OrderServices orderServices = new OrderServicesImpl(orderRepository);
    Orders testOrder = new Orders("testUser", LocalDateTime.now(), LocalDateTime.now());
    @Test
    void order() {
//        User testUser =new User("nhnacademy-test-user","nhn아카데미","nhnacademy-test-password","19900505", User.Auth.ROLE_USER,100_0000, LocalDateTime.now(),LocalDateTime.now());
//        OrderDetails testOrderDetails = new OrderDetails(3, 3, 3, 10);
//
//        when(orderRepository.save(testOrder)).thenReturn(1);
//        when(orderDetailRepository.save(testOrderDetails))
//                .thenReturn(1);
//        when(orderDetailRepository.findOrderDetailByOrderIdAndProductId(anyInt(), anyInt()))
//                .thenReturn(Optional.of(testOrderDetails));
//        when(shoppingCartService.delete(anyString(), anyInt()))
//                .thenReturn(1);
//        when(userService.getUser(anyString()))
//                .thenReturn(testUser);
//        when(orderDetailRepository.getTotalCost(anyInt()))
//                .thenReturn(100);
//        when(userService.updateUser(any(User.class)))
//                .thenReturn(testUser.getUserPoint());
        when(orderRepository.order(any())).thenReturn(1);
        orderServices.easyOrder(testOrder);
        Mockito.verify(orderRepository, Mockito.times(1)).order(any());
//
//        Mockito.verify(orderRepository, Mockito.times(1)).save(testOrder);
//        Mockito.verify(orderDetailRepository, Mockito.times(1)).save(testOrderDetails);
//        Mockito.verify(orderDetailRepository, Mockito.times(1)).findOrderDetailByOrderIdAndProductId(anyInt(), anyInt());
//        Mockito.verify(shoppingCartService, Mockito.times(1)).delete(anyString(), anyInt());
//        Mockito.verify(userService, Mockito.times(1)).getUser(anyString());
//        Mockito.verify(orderDetailRepository, Mockito.times(1)).getTotalCost(anyInt());
//        Mockito.verify(userService, Mockito.times(1)).updateUser(any(User.class));


    }
}