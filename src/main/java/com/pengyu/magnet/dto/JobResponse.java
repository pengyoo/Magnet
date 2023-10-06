package com.pengyu.magnet.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pengyu.magnet.domain.Company;
import com.pengyu.magnet.domain.Job;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Job Request DTO
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobResponse {

    private Long id;

    @JsonProperty("company")
    private CompanyResponse companyData;
    private String title;
    private String description;
    private String salaryRange;
    private String location;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireAt;

    private Job.Status status;
}
