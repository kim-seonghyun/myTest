package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 * 비즈니스 로직을 담당하는 서비스 계층
 */
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public int additionalPoint(String user_id, int pointToAdd) {
        Optional<User> user = userRepository.findById(user_id);

        if (user.isPresent()) {
            User presentUser = user.get();
            presentUser.setUserPoint(presentUser.getUserPoint() + pointToAdd);
            return userRepository.update(presentUser);
        }else{
            throw new UserNotFoundException(user_id);
        }

    }

    @Override
    public User getUser(String userId){
        //todo#4-1 회원조회
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    @Override
    public void saveUser(User user) {
        //todo#4-2 회원등록
        if (userRepository.countByUserId(user.getUserId()) >= 1) {
            throw new UserAlreadyExistsException(user.getUserId());
        }
        int result = userRepository.save(user);
        log.debug("saveUser 결과" + result);
        if (result < 1) {
            throw new RuntimeException("Do not save user");
        }
    }

    @Override
    public int updateUser(User user) {
        if (userRepository.countByUserId(user.getUserId()) == 0) {
            throw  new UserNotFoundException(user.getUserId());
        }
        //todo#4-3 회원수정
        return userRepository.update(user);
    }

    @Override
    public void deleteUser(String userId) {
        //todo#4-4 회원삭제
        if (userRepository.countByUserId(userId) == 0) {
            throw new UserNotFoundException(userId);
        }
        userRepository.deleteByUserId(userId);

    }

    @Override
    public int savePoint(String userId, int userPoint) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            User presentUser = user.get();
            presentUser.setUserPoint(userPoint);
            return userRepository.update(presentUser);
        }else{
            throw new UserNotFoundException(userId);
        }

    }

    /**
     * 로그인 성공시 유저정보 반환
     * @param userId
     * @param userPassword
     * @return
     */
    @Override
    public User doLogin(String userId, String userPassword) {
        //todo#4-5 로그인 구현, userId, userPassword로 일치하는 회원 조회
        Optional<User> optionalUser = userRepository.findByUserIdAndUserPassword(userId, userPassword);
        if (optionalUser.isPresent()) {
            userRepository.updateLatestLoginAtByUserId(userId, LocalDateTime.now());
            return optionalUser.get();
        }else{
            throw new UserNotFoundException(userId);
        }
    }

    @Override
    public int getPoint(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get().getUserPoint();
        }else{
            throw new UserNotFoundException(userId);
        }

    }

    @Override
    public boolean canLogin(String userId, String userPassword) {
        Optional<User> optionalUser = userRepository.findByUserIdAndUserPassword(userId, userPassword);
        if (optionalUser.isPresent()) {
            userRepository.updateLatestLoginAtByUserId(userId, LocalDateTime.now());
            return true;
        }else{
            return false;
        }
    }

}
