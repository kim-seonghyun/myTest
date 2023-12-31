package com.nhnacademy.shoppingmall.check.user.service.impl;

import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

//todo#4-6 Test Code가 통과하도록 UserServiceImpl을 구현합니다.

@Slf4j
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    UserService userService = new UserServiceImpl(userRepository);
    User testUser =new User("nhnacademy-test-user","nhn아카데미","nhnacademy-test-password","19900505", User.Auth.ROLE_USER,100_0000, LocalDateTime.now(),LocalDateTime.now());

    @Test
    @DisplayName("getUser")
    void getUser() {
        Mockito.when(userRepository.findById(anyString())).thenReturn(Optional.of(testUser));
        userService.getUser(testUser.getUserId());
        Mockito.verify(userRepository,Mockito.times(1)).findById(anyString());
    }

    @Test
    @DisplayName("getUser - user not found -> return null")
    void getUser_not_found(){
        Mockito.when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        User user = userService.getUser(testUser.getUserId());
        Assertions.assertNull(user);
    }

    @Test
    @DisplayName("save user")
    void saveUser() {
        //when은 test를 위해 userRepository의 countByUserId, save() 메소드의 동작을 가짜로 만듬.

        // countByUserId 메소드가 호출될때 0을 반환하도록 한다.
        // 사용자가 없는 상황을 가정
        Mockito.when(userRepository.countByUserId(anyString())).thenReturn(0);
        // save 메소드가 실행될때 1을 반환하도록 함.
        // 사용자가 성공적으로 저장됐다는 뜻
        Mockito.when(userRepository.save(any())).thenReturn(1);
        // 테스트할 메소드 saveUser 테스트
        userService.saveUser(testUser);
        // userRepository의 countByUserId 메소드가 정확히 한번 호출됐는지 확인
        Mockito.verify(userRepository,Mockito.times(1)).countByUserId(anyString());
        // save() 메소드가 정확히 한번 호출됐는지 확인.
        Mockito.verify(userRepository,Mockito.times(1)).save(any());

    }

    //Service만 확인하겠다?
    // userRepository의 countByUserId를 호출하면 1을 리턴한다.
    @Test
    @DisplayName("save user -aready exist user")
    void saveUser_exist(){
        Mockito.when(userRepository.countByUserId(anyString())).thenReturn(1);
        Throwable throwable = Assertions.assertThrows(UserAlreadyExistsException.class,()->userService.saveUser(testUser));
        log.debug("error:{}",throwable.getMessage());
    }

    @Test
    @DisplayName("update user")
    void updateUser() {
        Mockito.when(userRepository.countByUserId(anyString())).thenReturn(1);
        Mockito.when(userRepository.update(testUser)).thenReturn(1);
        userService.updateUser(testUser);
        Mockito.verify(userRepository,Mockito.times(1)).update(any());
        Mockito.verify(userRepository,Mockito.times(1)).countByUserId(anyString());
    }

    @Test
    @DisplayName("delete user")
    void deleteUser() {
        Mockito.when(userRepository.deleteByUserId(anyString())).thenReturn(1);
        Mockito.when(userRepository.countByUserId(anyString())).thenReturn(1);

        userService.deleteUser(testUser.getUserId());

        Mockito.verify(userRepository,Mockito.times(1)).deleteByUserId(anyString());
        Mockito.verify(userRepository,Mockito.times(1)).countByUserId(anyString());

    }

    @Test
    @DisplayName("login - success")
    void doLogin() {
        Mockito.when(userRepository.findByUserIdAndUserPassword(anyString(),anyString())).thenReturn(Optional.ofNullable(testUser));
        Mockito.when(userRepository.updateLatestLoginAtByUserId(anyString(),any())).thenReturn(1);

        User user = userService.doLogin(testUser.getUserId(), testUser.getUserPassword());

        Assertions.assertEquals(testUser,user);
        Mockito.verify(userRepository,Mockito.times(1)).findByUserIdAndUserPassword(anyString(),anyString());
        Mockito.verify(userRepository,Mockito.times(1)).updateLatestLoginAtByUserId(anyString(),any());
    }

    //그냥 메서드 호출되는지 확인하는거네.
    @Test
    @DisplayName("login fail")
    void doLogin_fail(){
        Mockito.when(userRepository.findByUserIdAndUserPassword(anyString(),anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class,()->userService.doLogin(testUser.getUserId(), testUser.getUserPassword()));
        Mockito.verify(userRepository,Mockito.times(1)).findByUserIdAndUserPassword(anyString(),anyString());
    }
}