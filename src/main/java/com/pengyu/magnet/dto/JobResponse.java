package com.pengyu.magnet.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pengyu.magnet.domain.Company;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Job Request DTO
 */
@Data
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
}
