package com.pengyu.magnet.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Resume Entity
 */
@Entity
@Data
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String profile;

    // One JOB_SEEKER can have one RESUME
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "resume_user_id_fk")
    )
    private User user;

    // One Resume has one contact info
    @OneToOne(mappedBy = "resume")
    private ContactInformation contactInformation;

    // One resume has multiple education experiences
    @OneToMany(mappedBy = "resume")
    private List<Education> educationList;

    // One resume has multiple work experiences
    @OneToMany(mappedBy = "resume")
    private List<WorkExperience> workExperienceList;

    // One resume has multiple skills
    @OneToMany(mappedBy = "resume")
    private List<Skill> skillList;

}
