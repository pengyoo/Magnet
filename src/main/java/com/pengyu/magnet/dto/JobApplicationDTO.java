package com.pengyu.magnet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pengyu.magnet.domain.JobApplication;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * JobApplication Entity (It's like a relationship table)
 */
@Data
public class JobApplicationDTO {

    private Long id;

    @JsonProperty(value = "user")
    private UserResponse userData;

    @JsonProperty(value = "job")
    private JobResponse jobData;

    private ResumeDTO resume;

    private MatchingIndexDTO matchingIndex;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime appliedDate;
    private JobApplication.Status status;

}
