package com.pengyu.magnet.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

/**
 * JobApplication Entity (It's like a relationship table)
 */
@Data
@Entity
@Table(name = "job_application")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
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
    @Enumerated(EnumType.STRING)
    private Status status;

    // Application Status Enum
    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED
    }
}
