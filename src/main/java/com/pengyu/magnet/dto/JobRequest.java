package com.pengyu.magnet.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.pengyu.magnet.domain.Company;
import jakarta.persistence.*;
import lombok.Data;
import org.aspectj.lang.annotation.After;

import java.time.LocalDateTime;

/**
 * Job Request DTO
 */
@Data
public class JobRequest {

    private Long id;
    private String title;
    private String description;
    private String salaryRange;
    private String location;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireAt;
}
