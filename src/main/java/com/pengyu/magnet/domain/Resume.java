package com.pengyu.magnet.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Resume Entity
 */
@Entity
@Data
@Table(name = "resume")
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


    /**
     * ContactInformation Entity for Resume
     */
    @Data
    @Entity
    @Table(name = "resume_contact_information")
    class ContactInformation {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String phoneNumber;
        private String email;
        private String address;
        private String city;
        private String country;
        private String linkedInUrl;

        // One contactInformation belongs to one resume
        @OneToOne
        @JoinColumn(name = "resume_id",
                referencedColumnName = "id",
                foreignKey = @ForeignKey(name = "contact_resume_id_fk")
        )
        private Resume resume;
    }

    /**
     * Skill Entity
     */
    @Data
    @Entity
    @Table(name = "resume_skill")
    class Skill {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String skill;

        // One resume can have multiple Skills
        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(
                name = "resume_id",
                referencedColumnName = "id",
                foreignKey = @ForeignKey(name = "skill_resume_id_fk")
        )
        private Resume resume;
    }

    /**
     * Education experience Entity
     */
    @Entity
    @Data
    @Table(name = "resume_education")
    class Education {

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

    /**
     * Work Experience Entity
     */
    @Entity
    @Data
    @Table(name = "resume_work_experience")
    class WorkExperience {

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

}
