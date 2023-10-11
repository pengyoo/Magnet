package com.pengyu.magnet.service.user;

import com.pengyu.magnet.dto.UserLoginRequest;
import com.pengyu.magnet.dto.UserRegisterRequest;
import com.pengyu.magnet.dto.UserRequest;
import com.pengyu.magnet.dto.UserResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * User Service Interface: define business feature functions for User domain
 */
public interface UserService {
    UserResponse register(UserRegisterRequest registerRequest);
    UserResponse login(UserLoginRequest registerRequest);

    long count();

    List<UserResponse> findAll(Pageable pageable);

    UserResponse find(Long id);

    UserResponse save(UserRequest userRequest);
}
