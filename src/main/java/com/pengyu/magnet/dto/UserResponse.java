package com.pengyu.magnet.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * User Response DTO
 */

@Data
public class UserResponse {
    private Long id;
    private String email;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private String role;
    private String token;
}
