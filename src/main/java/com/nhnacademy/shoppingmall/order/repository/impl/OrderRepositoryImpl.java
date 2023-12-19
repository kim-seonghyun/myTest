package com.nhnacademy.shoppingmall.order.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.order.domain.Orders;
import com.nhnacademy.shoppingmall.order.exceptions.InsufficientBalanceException;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.orderdetail.domain.OrderDetails;
import com.nhnacademy.shoppingmall.orderdetail.repository.OrderDetailRepository;
import com.nhnacademy.shoppingmall.products.repository.ProductsRepository;
import com.nhnacademy.shoppingmall.shoppingCart.service.ShoppingCartService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class OrderRepositoryImpl implements OrderRepository {
    UserRepository userRepository;

    OrderRepository orderRepository;
    OrderDetailRepository orderDetailRepository;

    ShoppingCartService shoppingCartService;

    ProductsRepository productsRepository;

    UserService userService;

    public OrderRepositoryImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,
                               ShoppingCartService shoppingCartService, ProductsRepository productsRepository,
                               UserService userService) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.shoppingCartService = shoppingCartService;
        this.productsRepository = productsRepository;
        this.userService = userService;
    }

    public OrderRepositoryImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public int save(Orders orders) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into Orders(user_id, OrderDate, ShipDate) VALUES (?,?,?)";
        if (userRepository.countByUserId(orders.getUserId()) <= 0) {
            throw new UserNotFoundException("등록되지 않은 userId입니다.");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) ) {
            preparedStatement.setString(1, orders.getUserId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(orders.getOrderDate()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(orders.getShipDate()) );

            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }else{
                    throw new SQLException("키가 생성되지 않았습니다.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Orders> findByOrderId(int orderId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from Orders where OrderID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);

            ResultSet rs = preparedStatement.executeQuery();
            log.debug("실행됨");
            if (rs.next()) {

                Orders orders = new Orders(rs.getString("user_id"), rs.getTimestamp("OrderDate").toLocalDateTime(), rs.getTimestamp("ShipDate").toLocalDateTime());

                return Optional.of(orders);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int update(Orders orders) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update Orders set user_id = ?,OrderDate = ? , ShipDate = ?";
        if (userRepository.countByUserId(orders.getUserId()) <= 0) {
            throw new UserNotFoundException("등록되지 않은 userId입니다.");
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, orders.getUserId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(orders.getOrderDate()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(orders.getShipDate()));

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(int orderId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete from Orders where OrderID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int order(Orders orders) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select P.UnitCost, P.ProductID, SC.Quantity from Products P join ShoppingCart SC on P.ProductID = SC.ProductID where SC.CartID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int orderId = this.save(orders);
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
