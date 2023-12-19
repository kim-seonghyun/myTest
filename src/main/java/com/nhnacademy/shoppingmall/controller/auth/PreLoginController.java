package com.nhnacademy.shoppingmall.controller.auth;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import com.google.gson.JsonObject;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(name = "preLoginServlet", urlPatterns = "/preLogin")
public class PreLoginController extends HttpServlet {
    private UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DbConnectionThreadLocal.initialize();

        StringBuilder jsonBuffer = new StringBuilder();
        String line = null;

        BufferedReader reader = req.getReader();
        while ((line = reader.readLine()) != null) {
            jsonBuffer.append(line);
        }


        String json = jsonBuffer.toString();
        Gson gson = new Gson();

        User user = gson.fromJson(json, User.class);

        String userId = user.getUserId();
        String userPassword = user.getUserPassword();


        String jsonUser = gson.toJson(user);
        log.debug("로그인할수있어?={}",userService.canLogin(userId, userPassword) );
        if(userService.canLogin(userId, userPassword)){
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(200);
            resp.getWriter().append(jsonUser).flush();
        }else{
            log.debug("실행됨");
            resp.setStatus(403);
            resp.getWriter().flush();
        }
        DbConnectionThreadLocal.reset();
    }
    private class User {
        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public String getUserId() {
            return userId;
        }

        public String getUserPassword() {
            return userPassword;
        }

        private String userId;
        private String userPassword;


    }


}
