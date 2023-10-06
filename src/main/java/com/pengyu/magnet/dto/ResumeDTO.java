package com.pengyu.magnet.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

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
    private List<EducationDTO> educations;
    private List<WorkExperienceDTO> workExperiences;

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

    }

    @Data
    public static class SkillDTO {
        private Long id;
        private String skill;
    }


    @Data
    public static class EducationDTO {

        private Long id;
        private String schoolName;
        private String degree;
        private String major;
        private LocalDate startDate;
        private LocalDate endDate;
    }

    @Data
    public static class WorkExperienceDTO {

        private Long id;
        private String position;
        private String companyName;
        private LocalDate startDate;
        private LocalDate endDate;
        private String description;
        private String location;
    }

}
