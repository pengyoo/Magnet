package com.pengyu.magnet.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.pengyu.magnet.domain.Company;
import com.pengyu.magnet.domain.Job;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.aspectj.lang.annotation.After;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Job Request DTO
 */
@Data
public class JobRequest {

    private Long id;

    @NotBlank(message = "Job title can not be blank.")
    private String title;
    @NotBlank(message = "Job description can not be blank.")
    private String description;
    private String salaryRange;
    private String location;
    private Job.Status status;

    @Future(message = "The date must be in the future")
    private LocalDate expireAt;
}
