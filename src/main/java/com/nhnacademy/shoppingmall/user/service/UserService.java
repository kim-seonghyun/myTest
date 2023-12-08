package com.nhnacademy.shoppingmall.user.service;

import com.nhnacademy.shoppingmall.user.domain.User;

//어떻게
public interface UserService {

    User getUser(String userId);

    void saveUser(User user);

    int updateUser(User user);

    void deleteUser(String userId);

    int savePoint(String userId, int userPoint);

    User doLogin(String userId, String userPassword);

    int getPoint(String userId);

}
