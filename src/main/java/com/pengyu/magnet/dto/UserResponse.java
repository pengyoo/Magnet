package com.pengyu.magnet.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pengyu.magnet.domain.Job;
import com.pengyu.magnet.domain.User;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * User Response DTO
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Long id;
    private String email;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private String role;
    private String token;

    private User.Status status;

}
