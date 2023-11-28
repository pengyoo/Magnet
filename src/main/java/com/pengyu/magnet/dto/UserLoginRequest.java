package com.pengyu.magnet.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * User Login Request DTO
 */
@Data
public class UserLoginRequest {

    @Email(message = "Invalid email address.")
    private String email;

    @NotBlank(message = "Password can not be blank.")
    private String password;
}
