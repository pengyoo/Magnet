package com.pengyu.magnet.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pengyu.magnet.domain.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * User Response DTO
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequest {
    private Long id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private User.Status status;
    @Enumerated(EnumType.STRING)
    private User.Role role;

}
