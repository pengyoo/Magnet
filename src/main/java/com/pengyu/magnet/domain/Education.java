package com.pengyu.magnet.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * Education experience Entity
 */
@Entity
@Data
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One resume can have multiple educations
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "resume_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "education_resume_id_fk")
    )
    private Resume resume;
    private String schoolName;
    private String degree;
    private String major;
    private LocalDate startDate;
    private LocalDate endDate;
}
