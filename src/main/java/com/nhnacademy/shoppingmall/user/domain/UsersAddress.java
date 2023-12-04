package com.nhnacademy.shoppingmall.user.domain;

import java.util.Objects;

public class UsersAddress {
    private final String userId;

    private final String addressId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UsersAddress that = (UsersAddress) o;
        return that.addressId.equals(addressId) && that.userId.equals(userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, addressId);
    }

    public String getUserId() {
        return userId;
    }

    public String getAddressId() {
        return addressId;
    }

    public UsersAddress(String userId, String addressId) {
        this.userId = userId;
        this.addressId = addressId;
    }
}
