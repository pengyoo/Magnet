package com.pengyu.magnet.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * Work Experience Entity
 */
@Entity
@Data
public class WorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One resume has multiple work experiences
    @ManyToOne
    @JoinColumn(
            name = "resume_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "work_experience_resume_id_fk")
    )
    private Resume resume;

    private String position;
    private String companyName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String location;
}
