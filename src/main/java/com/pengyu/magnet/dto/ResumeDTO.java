package com.pengyu.magnet.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pengyu.magnet.domain.Resume;
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

    private String fullName;

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
        private String phoneNumber;
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
        private String skill;
        private String key = UUID.randomUUID().toString();
    }


    @Data
    public static class EducationDTO {

        private Long id;
        private String schoolName;
        private String degree;
        private String major;
        private LocalDate startDate;
        private LocalDate endDate;
        private String key = UUID.randomUUID().toString();
    }

    @Data
    public static class ExperienceDTO {

        private Long id;
        private String position;
        private String companyName;
        private LocalDate startDate;
        private LocalDate endDate;
        private String description;
        private String location;
        private String key = UUID.randomUUID().toString();
    }

    @Data
    public static class ProjectDTO {

        private Long id;
        private String name;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private String description;
        private String key = UUID.randomUUID().toString();
    }

}
