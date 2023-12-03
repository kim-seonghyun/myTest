package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 비즈니스 로직을 담당하는 서비스 계층
 */
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String userId){
        //todo#4-1 회원조회
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    @Override
    public void saveUser(User user) {
        //todo#4-2 회원등록
        if (userRepository.countByUserId(user.getUserId()) >= 1) {
            throw new UserAlreadyExistsException(user.getUserId());
        }
        int result = userRepository.save(user);
        if (result < 1) {
            throw new RuntimeException("Do not save user");
        }
    }

    @Override
    public void updateUser(User user) {
        if (userRepository.countByUserId(user.getUserId()) == 0) {
            throw  new UserNotFoundException(user.getUserId());
        }
        //todo#4-3 회원수정
        userRepository.update(user);
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

}
