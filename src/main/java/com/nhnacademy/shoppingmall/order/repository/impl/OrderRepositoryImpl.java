package com.nhnacademy.shoppingmall.order.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.order.domain.Orders;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
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
}
