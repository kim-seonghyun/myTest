package com.nhnacademy.shoppingmall.order.repository;

import com.nhnacademy.shoppingmall.order.domain.Orders;
import java.util.Optional;

public interface OrderRepository {
    int save(Orders orders);

    Optional<Orders> findByOrderId(int orderId);

    int update(Orders orders);

    int delete(int orderId);
}
