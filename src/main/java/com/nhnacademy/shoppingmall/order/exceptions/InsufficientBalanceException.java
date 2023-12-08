package com.nhnacademy.shoppingmall.order.exceptions;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String userId){
        super(String.format("%s의 잔액이 부족합니다.", userId));}
}
