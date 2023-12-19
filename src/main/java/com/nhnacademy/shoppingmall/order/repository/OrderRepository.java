package com.nhnacademy.shoppingmall.order.repository;

import com.nhnacademy.shoppingmall.order.domain.Orders;
import java.util.Optional;

public interface OrderRepository {
    int save(Orders orders);

    Optional<Orders> findByOrderId(int orderId);

    int update(Orders orders);

    int delete(int orderId);

    /**
     * 주문하는 method입니다.
     * @param order
     * @return
     */
    int order(Orders order);
}
