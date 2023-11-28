package com.pengyu.magnet.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pengyu.magnet.domain.Resume;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * ResumeDTO
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResumeDTO {

    private Long id;

    @NotBlank(message = "Name can not be blank.")
    private String fullName;

    @NotBlank(message = "Profile can not be blank.")
    private String profile;

    private ContactInformationDTO contact;
    private List<SkillDTO> skills;
    private List<EducationDTO> education;
    private List<ExperienceDTO> experience;
    private List<ProjectDTO> projects;

    private Resume.Status status;

    @Data
    public static class ContactInformationDTO {

        private Long id;

        @NotBlank(message = "Phone Number can not be blank.")
        private String phoneNumber;

        @Email(message = "Email is invalid.")
        private String email;

        private String address;
        private String city;
        private String country;
        private String postCode;
        private String linkedInUrl;

        private String key = UUID.randomUUID().toString();

    }

    @Data
    public static class SkillDTO {
        private Long id;

        @NotBlank(message = "Skill can not be blank.")
        private String skill;
        private String key = UUID.randomUUID().toString();
    }


    @Data
    public static class EducationDTO {

        private Long id;

        @NotBlank(message = "School name can not be blank.")
        private String schoolName;

        @NotBlank(message = "Degree can not be blank.")
        private String degree;

        @NotBlank(message = "Major can not be blank.")
        private String major;

        @Past(message = "The date must be in the past")
        private LocalDate startDate;
        private LocalDate endDate;
        private String key = UUID.randomUUID().toString();
    }

    @Data
    public static class ExperienceDTO {

        private Long id;

        @NotBlank(message = "Position name can not be blank.")
        private String position;

        @NotBlank(message = "Company Name can not be blank.")
        private String companyName;

        @Past(message = "The date must be in the past")
        private LocalDate startDate;
        private LocalDate endDate;
        private String description;
        private String location;
        private String key = UUID.randomUUID().toString();
    }

    @Data
    public static class ProjectDTO {

        private Long id;

        @NotBlank(message = "Project Name can not be blank.")
        private String name;

        @Past(message = "The date must be in the past.")
        private LocalDate startDate;
        private LocalDate endDate;
        private String description;
        private String key = UUID.randomUUID().toString();
    }

}
