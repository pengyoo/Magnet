package com.pengyu.magnet.service.user;

import com.pengyu.magnet.dto.UserLoginRequest;
import com.pengyu.magnet.dto.UserRegisterRequest;
import com.pengyu.magnet.dto.UserResponse;

/**
 * User Service Interface: define business feature functions for User domain
 */
public interface UserService {
    UserResponse register(UserRegisterRequest registerRequest);
    UserResponse login(UserLoginRequest registerRequest);
}
