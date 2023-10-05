package com.pengyu.magnet.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Job Entity
 */
@Entity
@Data
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One company has multiple jobs
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "company_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "job_company_id_fk")
    )
    private Company company;
    private String title;
    private String description;
    private String salaryRange;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime expireAt;
}
