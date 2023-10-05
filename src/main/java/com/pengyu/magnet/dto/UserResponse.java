package com.pengyu.magnet.dto;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * User Response DTO
 */

@Data
public class UserResponse {
    private Long id;
    private String email;
    private LocalDateTime createdAt;
    private String role;
}
