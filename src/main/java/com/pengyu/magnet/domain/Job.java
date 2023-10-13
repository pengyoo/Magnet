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
// Add Index for title
@Table(name = "job", indexes = @Index(columnList="title"))
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One company has multiple jobs
    @ManyToOne
    @JoinColumn(
            name = "company_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "job_company_id_fk")
    )
    private Company company;

    private String title;

    @Column(columnDefinition = "text")
    private String description;
    private String salaryRange;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime expireAt;

    private Status status;

    public enum Status {
        ACTIVE,
        EXPIRED,
        DELETED
    }
}
