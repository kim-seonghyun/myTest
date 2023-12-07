package com.nhnacademy.shoppingmall.orderdetail.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.orderdetail.domain.OrderDetails;
import com.nhnacademy.shoppingmall.orderdetail.repository.OrderDetailRepository;
import com.nhnacademy.shoppingmall.products.repository.ProductsRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderDetailRepositoryImpl implements OrderDetailRepository {
    ProductsRepository productsRepository;
    OrderRepository orderRepository;

    public OrderDetailRepositoryImpl(ProductsRepository productsRepository, OrderRepository orderRepository) {
        this.productsRepository = productsRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public int save(OrderDetails orderDetails) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "insert into OrderDetails(OrderID, ProductID, Quantity, UnitCost) VALUES (?,?,?,?)";

        if (productsRepository.findByProductId(orderDetails.getProductId()).isEmpty()) {
            throw new RuntimeException("product가 존재하지 않습니다.");
        }

        if (orderRepository.findByOrderId(orderDetails.getOrderId()).isEmpty()) {
            throw new RuntimeException("Order가 존재하지 않습니다.");
        }

        if (findOrderDetailByOrderIdAndProductId(orderDetails.getOrderId(), orderDetails.getProductId()).isPresent()) {
            throw new RuntimeException("중복된 orderdetails입니다.");
        }

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderDetails.getOrderId());
            preparedStatement.setInt(2, orderDetails.getProductId());
            preparedStatement.setInt(3, orderDetails.getQuantity());
            preparedStatement.setInt(4, orderDetails.getUnitCost());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderDetails> findOrderDetailByOrderId(int orderId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from OrderDetails where OrderID = ?";

        if (orderRepository.findByOrderId(orderId).isEmpty()) {
            throw new RuntimeException("주문이 존재하지 않습니다");
        }

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);

            ResultSet rs = preparedStatement.executeQuery();
            List<OrderDetails> orderDetailsList = new ArrayList<>();
            while (rs.next()) {
                orderDetailsList.add(
                        new OrderDetails(rs.getInt("OrderID"), rs.getInt("ProductID"), rs.getInt("Quantity"),
                                rs.getInt("UnitCost")));
            }
            return  orderDetailsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<OrderDetails> findOrderDetailByOrderIdAndProductId(int orderId, int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select * from OrderDetails where OrderID = ? and ProductID = ?";

        if (orderRepository.findByOrderId(orderId).isEmpty()) {
            throw new RuntimeException("주문이 존재하지 않습니다");
        }

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, productId);

            ResultSet rs = preparedStatement.executeQuery();
            List<OrderDetails> orderDetailsList = new ArrayList<>();
            if (rs.next()) {
                return Optional.of(new OrderDetails(rs.getInt("OrderID"), rs.getInt("ProductID"), rs.getInt("Quantity"), rs.getInt("UnitCost")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public int update(OrderDetails orderDetails) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update OrderDetails set Quantity = ?, UnitCost =?";

        if (productsRepository.findByProductId(orderDetails.getProductId()).isEmpty()) {
            throw new RuntimeException("product가 존재하지 않습니다.");
        }

        if (orderRepository.findByOrderId(orderDetails.getOrderId()).isEmpty()) {
            throw new RuntimeException("Order가 존재하지 않습니다.");
        }

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderDetails.getQuantity());
            preparedStatement.setInt(2, orderDetails.getUnitCost());

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteOrderDetail(int orderId, int productId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "delete  from OrderDetails where OrderID = ? and ProductID =?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, productId);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
