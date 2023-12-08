package com.nhnacademy.shoppingmall.order.services.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.order.domain.Orders;
import com.nhnacademy.shoppingmall.order.exceptions.InsufficientBalanceException;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.order.services.OrderServices;
import com.nhnacademy.shoppingmall.orderdetail.domain.OrderDetails;
import com.nhnacademy.shoppingmall.orderdetail.repository.OrderDetailRepository;
import com.nhnacademy.shoppingmall.products.repository.ProductsRepository;
import com.nhnacademy.shoppingmall.shoppingCart.service.ShoppingCartService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.UserService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.DelegatingConnection;

@Slf4j
public class OrderServicesImpl implements OrderServices {
    // Order, orderDetail, ShoppingCart, Product, users

    OrderRepository orderRepository;
    OrderDetailRepository orderDetailRepository;

    ShoppingCartService shoppingCartService;

    ProductsRepository productsRepository;

    UserService userService;

    public OrderServicesImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,
                             ShoppingCartService shoppingCartService, ProductsRepository productsRepository,
                             UserService userService) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.shoppingCartService = shoppingCartService;
        this.productsRepository = productsRepository;
        this.userService = userService;
    }


    @Override
    public int order(Orders orders) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select P.UnitCost, P.ProductID, SC.Quantity from Products P join ShoppingCart SC on P.ProductID = SC.ProductID where SC.CartID = ?";
        log.debug("order 실행됨");
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int orderId= orderRepository.save(orders);
            log.debug("orderID order실행중:" +String.valueOf(orderId));
            preparedStatement.setString(1, orders.getUserId());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                OrderDetails myOrderDetails = new OrderDetails(orderId, rs.getInt("ProductID"), rs.getInt("Quantity"),
                        rs.getInt("UnitCost"));

                orderDetailRepository.save(myOrderDetails);
                if (orderDetailRepository.findOrderDetailByOrderIdAndProductId(myOrderDetails.getOrderId(),
                        myOrderDetails.getProductId()).isEmpty()) {
                    throw new RuntimeException("orderDetail 저장안됨");
                }

                int deleteResult = shoppingCartService.delete(orders.getUserId(), myOrderDetails.getProductId());
                if (deleteResult < 1) {
                    throw new RuntimeException("shopping cart 삭제 안됨.");
                }
            }
            List<OrderDetails> orderDetailsList = orderDetailRepository.findOrderDetailByOrderId(orderId);


            int totalCost =  orderDetailsList.stream()
                    .mapToInt(orderDetails -> orderDetails.getQuantity() * orderDetails.getUnitCost()).sum();

            User user = userService.getUser(orders.getUserId());
            // int로 부족한경우는?
            int userPoint = user.getUserPoint();
            if (userPoint < totalCost) {
                throw new InsufficientBalanceException(user.getUserId());
            }
            userPoint -= totalCost;
            user.setUserPoint(userPoint);
            log.debug("OrderService - 성공");
            return userService.updateUser(user);
            //포인트 적립 요청.
        } catch (RuntimeException | SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException(e);
        }
    }
}
