package com.nhnacademy.shoppingmall.order.services;

import com.nhnacademy.shoppingmall.order.domain.Orders;

public interface OrderServices {
     int order(Orders orders);

     int easyOrder(Orders orders);

}
