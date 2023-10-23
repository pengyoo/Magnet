package com.pengyu.magnet.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Resume Entity
 */
@Entity
@Data
@Table(name = "resume")
@DynamicUpdate
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(columnDefinition = "text")
    private String profile;

    // One JOB_SEEKER can have one RESUME
    @OneToOne
    @JoinColumn(name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "resume_user_id_fk")
    )
    private User user;

    // One Resume has one contact info
    @OneToOne(mappedBy = "resume", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ContactInformation contactInformation;

    // One resume has multiple education experiences
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Education> educationList;

    // One resume has multiple work experiences
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Experience> workExperienceList;

    // One resume has multiple skills
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Skill> skillList;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Project> projectList;

    private LocalDateTime createdAt;

    private Status status;

    public enum Status {
        ACTIVE,
        DELETED
    }


    /**
     * ContactInformation Entity for Resume
     */
    @Data
    @Entity
    @Table(name = "resume_contact_information")
    public static class ContactInformation {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String phoneNumber;
        private String email;
        private String address;
        private String city;
        private String country;
        private String postCode;
        private String linkedInUrl;



        // One contactInformation belongs to one resume
        @OneToOne
        @JoinColumn(name = "resume_id",
                referencedColumnName = "id",
                foreignKey = @ForeignKey(name = "contact_resume_id_fk")
        )
        private Resume resume;

        @Override
        public String toString() {
            return "ContactInformation{" +
                    "id=" + id +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    ", email='" + email + '\'' +
                    ", address='" + address + '\'' +
                    ", city='" + city + '\'' +
                    ", country='" + country + '\'' +
                    ", postCode='" + postCode + '\'' +
                    ", linkedInUrl='" + linkedInUrl + '\'' +
                    '}';
        }
    }

    /**
     * Skill Entity
     */
    @Data
    @Entity
    @Table(name = "resume_skill")
    public static class Skill {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String skill;

        // One resume can have multiple Skills
        @ManyToOne
        @JoinColumn(
                name = "resume_id",
                referencedColumnName = "id",
                foreignKey = @ForeignKey(name = "skill_resume_id_fk")
        )
        @JsonIgnore
        private Resume resume;

        @Override
        public String toString() {
            return "Skill{" +
                    "id=" + id +
                    ", skill='" + skill + '\'' +
                    '}';
        }
    }

    /**
     * Education experience Entity
     */
    @Entity
    @Data
    @Table(name = "resume_education")
    public static class Education {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        // One resume can have multiple educations
        @ManyToOne
        @JoinColumn(
                name = "resume_id",
                referencedColumnName = "id",
                foreignKey = @ForeignKey(name = "education_resume_id_fk")
        )
        @JsonIgnore
        private Resume resume;
        private String schoolName;
        private String degree;
        private String major;
        private LocalDate startDate;
        private LocalDate endDate;

        @Override
        public String toString() {
            return "Education{" +
                    "id=" + id +
                    ", schoolName='" + schoolName + '\'' +
                    ", degree='" + degree + '\'' +
                    ", major='" + major + '\'' +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    '}';
        }
    }

    /**
     * Work Experience Entity
     */
    @Entity
    @Data
    @Table(name = "resume_work_experience")
    public static class Experience {

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
        @JsonIgnore
        private Resume resume;

        private String position;
        private String companyName;
        private LocalDate startDate;
        private LocalDate endDate;
        @Column(columnDefinition = "text")
        private String description;
        private String location;

        @Override
        public String toString() {
            return "Experience{" +
                    "id=" + id +
                    ", position='" + position + '\'' +
                    ", companyName='" + companyName + '\'' +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", description='" + description + '\'' +
                    ", location='" + location + '\'' +
                    '}';
        }
    }

    @Entity
    @Data
    @Table(name = "resume_project")
    public static class Project {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        // One resume has multiple work experiences
        @ManyToOne
        @JoinColumn(
                name = "resume_id",
                referencedColumnName = "id",
                foreignKey = @ForeignKey(name = "project_resume_id_fk")
        )
        @JsonIgnore
        private Resume resume;

        private String name;
        private LocalDate startDate;
        private LocalDate endDate;
        @Column(columnDefinition = "text")
        private String description;


        @Override
        public String toString() {
            return "Project{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Resume{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", profile='" + profile + '\'' +
                ", user=" + user +
                ", contactInformation=" + contactInformation +
                ", educationList=" + educationList +
                ", workExperienceList=" + workExperienceList +
                ", skillList=" + skillList +
                ", projectList=" + projectList +
                ", createdAt=" + createdAt +
                ", status=" + status +
                '}';
    }
}
