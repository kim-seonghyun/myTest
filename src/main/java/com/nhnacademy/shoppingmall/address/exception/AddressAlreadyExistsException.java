package com.nhnacademy.shoppingmall.address.exception;

public class AddressAlreadyExistsException extends RuntimeException {
    public AddressAlreadyExistsException(){super(String.format("address가 이미 존재합니다."));}
}
