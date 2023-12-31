package com.nhnacademy.shoppingmall.thread.request.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.thread.request.ChannelRequest;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PointChannelRequest extends ChannelRequest {
    private UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private String userId;
    private int additionalPoint;

    public PointChannelRequest(String userId, int additionalPoint) {
        this.userId = userId;
        this.additionalPoint = additionalPoint;
    }

    @Override
    public void execute() {
        // 이코드는 기존 db connection과 다른 connection에서 하기 위해 쓴거?
        DbConnectionThreadLocal.initialize();
        //todo#14-5 포인트 적립구현, connection은 point적립이 완료되면 반납합니다.
        try {

            userService.savePoint(userId, additionalPoint);
        } catch (Exception e) {
            log.error(e.getMessage());
            DbConnectionThreadLocal.setSqlError(true);
        }
        log.debug("pointChannel execute");
        DbConnectionThreadLocal.reset();
    }
}
