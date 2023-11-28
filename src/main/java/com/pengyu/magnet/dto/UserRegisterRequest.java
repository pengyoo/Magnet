package com.pengyu.magnet.dto;


import com.pengyu.magnet.domain.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * User Register Request DTO
 */
@Data
public class UserRegisterRequest {

    @Email(message = "Invalid email address.")
    @NotBlank(message = "Email can not be blank.")
    private String email;

    @NotBlank(message = "Password can not be blank.")
    @Size(min = 8, message = "Password must be longer than 8.")
    private String password;
    @Enumerated(EnumType.STRING)
    private User.Role role;
}
