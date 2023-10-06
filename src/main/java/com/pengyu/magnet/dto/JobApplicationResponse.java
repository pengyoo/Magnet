package com.pengyu.magnet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pengyu.magnet.domain.Job;
import com.pengyu.magnet.domain.JobApplication;
import com.pengyu.magnet.domain.User;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * JobApplication Entity (It's like a relationship table)
 */
@Data
public class JobApplicationResponse {

    private Long id;

    @JsonProperty(value = "user")
    private UserResponse userData;

    @JsonProperty(value = "job")
    private JobResponse jobData;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime appliedDate;
    private JobApplication.Status status;

}
