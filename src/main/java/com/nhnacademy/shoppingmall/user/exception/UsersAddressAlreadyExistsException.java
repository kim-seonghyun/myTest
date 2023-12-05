package com.nhnacademy.shoppingmall.user.exception;

public class UsersAddressAlreadyExistsException extends RuntimeException {
    public UsersAddressAlreadyExistsException(){
        super(String.format("usersAddress already exists"));
    }
}
