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

    public OrderServicesImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

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


    public int easyOrder(Orders orders){
        Connection connection = DbConnectionThreadLocal.getConnection();

        try {
            return orderRepository.order(orders);
        } catch (RuntimeException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException("주문 실패");
        }

    }

    @Override
    public int order(Orders orders) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select P.UnitCost, P.ProductID, SC.Quantity from Products P join ShoppingCart SC on P.ProductID = SC.ProductID where SC.CartID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int orderId = orderRepository.save(orders);
            log.debug("orderID order실행중:" + String.valueOf(orderId));
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
                    DbConnectionThreadLocal.setSqlError(true);
                    throw new RuntimeException("shopping cart 삭제 안됨.");
                }
            }

            User user = userService.getUser(orders.getUserId());
            int totalCost = orderDetailRepository.getTotalCost(orderId);

            // int로 부족한경우는?
            int userPoint = user.getUserPoint();
            if (userPoint < totalCost) {
                throw new InsufficientBalanceException(user.getUserId());
            }
            userPoint -= totalCost;
            user.setUserPoint(userPoint);
            log.debug("OrderService - 성공");
            userService.updateUser(user);
            return orderId;
            //포인트 적립 요청.
        } catch (InsufficientBalanceException e) {
            throw new InsufficientBalanceException(e.getMessage());
        }catch (RuntimeException | SQLException e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException(e);
        }
    }
}
