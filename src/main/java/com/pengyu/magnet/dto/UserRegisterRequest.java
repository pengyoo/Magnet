package com.pengyu.magnet.dto;


import com.pengyu.magnet.domain.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

/**
 * User Register Request DTO
 */
@Data
public class UserRegisterRequest {

    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private User.Role role;
}
