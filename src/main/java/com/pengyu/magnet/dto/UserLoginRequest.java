package com.pengyu.magnet.dto;


import lombok.Data;

/**
 * User Login Request DTO
 */
@Data
public class UserLoginRequest {

    private String email;
    private String password;
}
