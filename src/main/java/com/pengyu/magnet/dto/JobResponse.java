package com.pengyu.magnet.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pengyu.magnet.domain.Company;
import com.pengyu.magnet.domain.Job;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;
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
    private LocalDateTime createdAt;
    private LocalDate expireAt;
    private Job.Status status;
}
