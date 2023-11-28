package com.pengyu.magnet.controller;


import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.dto.*;
import com.pengyu.magnet.service.user.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody UserRegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }
    @PostMapping("/login")
    public UserResponse login(@RequestBody UserLoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return userService.find(id);
    }
    @GetMapping
    @RolesAllowed({CONSTANTS.ROLE_ADMIN})
    public List<UserResponse> findAll(@RequestParam(defaultValue = "0", required = false) Integer _start,
                                          @RequestParam(defaultValue = "10", required = false) Integer _end,
                                          @RequestParam(defaultValue = "id", required = false) String sort,
                                          @RequestParam(defaultValue = "desc", required = false) String order,
                                          HttpServletResponse response){
        // process sort factor
        Sort sortBy = "desc".equals(order) ? Sort.by(sort).descending() : Sort.by(sort).ascending();

        // create pageable
        int pageSize = _end - _start;
        int page = _start / (pageSize - 1);
        Pageable pageable = PageRequest.of(page, pageSize, sortBy);

        // Set Header
        String count = String.valueOf(userService.count());
        response.addHeader("x-total-count", count);
        response.addHeader("Access-Control-Expose-Headers", "x-total-count");

        return userService.findAll(pageable);
    }


    /**
     * Add User
     * @param userRequest
     * @return
     */
    @PostMapping
    @RolesAllowed({CONSTANTS.ROLE_ADMIN})
    public UserResponse add(@RequestBody UserRequest userRequest) {
        return userService.save(userRequest);
    }


    /**
     * Edit User
     * @param userRequest
     * @return
     */
    @PatchMapping("/{id}")
    public UserResponse patch(@RequestBody UserRequest userRequest, @PathVariable String id) {
        return userService.save(userRequest);
    }
}
