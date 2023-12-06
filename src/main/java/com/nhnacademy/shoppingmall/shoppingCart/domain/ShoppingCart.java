package com.nhnacademy.shoppingmall.shoppingCart.domain;

import java.sql.Timestamp;
import java.util.Objects;

public class ShoppingCart {
    private int recordId;
    private String cartId;
    private int quantity;
    private int productId;

    public ShoppingCart(String cartId, int quantity, int productId) {
        this.cartId = cartId;
        this.quantity = quantity;
        this.productId = productId;
    }

    private Timestamp dateCreated;

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getRecordId() {
        return recordId;
    }

    public String getCartId() {
        return cartId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getProductId() {
        return productId;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public ShoppingCart(int quantity, int productId) {
        this.quantity = quantity;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShoppingCart that = (ShoppingCart) o;
        return quantity == that.quantity && productId == that.productId && Objects.equals(cartId, that.cartId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, quantity, productId);
    }
}
