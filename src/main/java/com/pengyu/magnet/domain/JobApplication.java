package com.pengyu.magnet.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * JobApplication Entity (It's like a relationship table)
 */
@Data
@Entity
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One JOB_SEEKER can apply multiple jobs
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "application_user_id_fk")
    )
    private User user;

    // One JOB_SEEKER can apply multiple jobs
    @ManyToOne
    @JoinColumn(
            name = "job_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "application_job_id_fk")
    )
    private Job job;

    private LocalDateTime appliedDate;
    private Status status;

    // Application Status Enum
    public enum Status {
        PENDING_REVIEW,
        ACCEPTED,
        REJECTED
    }
}
