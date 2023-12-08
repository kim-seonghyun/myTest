package com.nhnacademy.shoppingmall.orderdetail.repository;

import com.nhnacademy.shoppingmall.orderdetail.domain.OrderDetails;
import java.util.List;
import java.util.Optional;
public interface OrderDetailRepository {

    int getTotalCost(int orderId);
    int save(OrderDetails orderDetails);

    List<OrderDetails> findOrderDetailByOrderId(int orderId);

    Optional<OrderDetails> findOrderDetailByOrderIdAndProductId(int orderId, int productId);

    int update(OrderDetails orderDetails);

    int deleteOrderDetail(int orderId, int productId);
}
