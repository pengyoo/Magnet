package com.pengyu.magnet.controller;


import com.pengyu.magnet.dto.UserLoginRequest;
import com.pengyu.magnet.dto.UserRegisterRequest;
import com.pengyu.magnet.dto.UserResponse;
import com.pengyu.magnet.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }
    @PostMapping("/login")
    public UserResponse login(@RequestBody UserLoginRequest loginRequest) {
        return userService.login(loginRequest);
    }
}
