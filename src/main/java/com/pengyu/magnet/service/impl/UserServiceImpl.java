package com.pengyu.magnet.service.impl;

import com.pengyu.magnet.domain.User;
import com.pengyu.magnet.dto.UserLoginRequest;
import com.pengyu.magnet.dto.UserRegisterRequest;
import com.pengyu.magnet.dto.UserResponse;
import com.pengyu.magnet.mapper.UserMapper;
import com.pengyu.magnet.repository.UserRepository;
import com.pengyu.magnet.security.JwtService;
import com.pengyu.magnet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Implement business features for User domain
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    /**
     * Register
     * @param registerRequest
     * @return
     */
    @Override
    public UserResponse register(UserRegisterRequest registerRequest) {
        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole())
                .createdAt(LocalDateTime.now())
                .isEnabled(true).build();
        user =  userRepository.save(user);

        UserResponse userResponse = UserMapper.INSTANCE.mapUserToUserResponse(user);

        // Authenticate
        String jwtToken = jwtService.generateToken(user);
        userResponse.setToken(jwtToken);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerRequest.getEmail(),
                        registerRequest.getPassword()
                )
        );

        return userResponse;
    }


    /**
     * Login
     * @param loginRequest
     * @return
     */
    @Override
    public UserResponse login(UserLoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        User user = userRepository.findByEmail(loginRequest.getEmail());
        String jwtToken = jwtService.generateToken(user);
        UserResponse authResponse = UserMapper.INSTANCE.mapUserToUserResponse(user);
        authResponse.setToken(jwtToken);
        return authResponse;
    }
}
