package com.pengyu.magnet.service.impl;

import com.pengyu.magnet.domain.User;
import com.pengyu.magnet.dto.UserLoginRequest;
import com.pengyu.magnet.dto.UserRegisterRequest;
import com.pengyu.magnet.dto.UserResponse;
import com.pengyu.magnet.mapper.UserMapper;
import com.pengyu.magnet.repository.UserRepository;
import com.pengyu.magnet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Implement business features for User domain
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserResponse register(UserRegisterRequest registerRequest) {
        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .role(registerRequest.getRole())
                .createdAt(LocalDateTime.now())
                .build();
        user =  userRepository.save(user);
        return UserMapper.INSTANCE.mapUserToUserResponse(user);
    }

    @Override
    public UserResponse login(UserLoginRequest registerRequest) {
        return null;
    }
}
