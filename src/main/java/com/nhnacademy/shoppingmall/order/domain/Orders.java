package com.nhnacademy.shoppingmall.order.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class Orders {
    private int orderId;
    private String userId;
    private LocalDateTime orderDate;
    private LocalDateTime shipDate;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setShipDate(LocalDateTime shipDate) {
        this.shipDate = shipDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Orders orders = (Orders) o;
        return Objects.equals(userId, orders.userId) && Objects.equals(orderDate, orders.orderDate)
                && Objects.equals(shipDate, orders.shipDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, orderDate, shipDate);
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public LocalDateTime getShipDate() {
        return shipDate;
    }

    public Orders(String userId, LocalDateTime orderDate, LocalDateTime shipDate) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.shipDate = shipDate;
    }
}
