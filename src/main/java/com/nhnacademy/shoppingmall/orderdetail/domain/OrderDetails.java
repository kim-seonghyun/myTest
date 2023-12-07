package com.nhnacademy.shoppingmall.orderdetail.domain;

import java.util.Objects;

public class OrderDetails {
    private int orderId;
    private int productId;
    private int quantity;
    private int unitCost;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderDetails that = (OrderDetails) o;
        return orderId == that.orderId && productId == that.productId && quantity == that.quantity
                && unitCost == that.unitCost;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId, quantity, unitCost);
    }

    public OrderDetails(int orderId, int productId, int quantity, int unitCost) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitCost = unitCost;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getUnitCost() {
        return unitCost;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnitCost(int unitCost) {
        this.unitCost = unitCost;
    }
}
