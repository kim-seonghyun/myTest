package com.nhnacademy.shoppingmall.ShoppingCart.domain;

import java.sql.Timestamp;

public class ShoppingCart {
    private int recordId;
    private String cartId;
    private int quantity;
    private int productId;

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

}
