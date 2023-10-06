package com.pengyu.magnet.service.impl;

import com.pengyu.magnet.domain.Resume;
import com.pengyu.magnet.domain.User;
import com.pengyu.magnet.dto.ResumeDTO;
import com.pengyu.magnet.exception.ResourceNotFoundException;
import com.pengyu.magnet.mapper.*;
import com.pengyu.magnet.repository.ResumeRepository;
import com.pengyu.magnet.repository.UserRepository;
import com.pengyu.magnet.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * CV service
 */
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    /**
     * Add or Edit Resume
     * @param resumeRequest
     * @return
     */
    @Override
    public ResumeDTO save(ResumeDTO resumeRequest) {
        // Get Current login user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);

        // Mapper ResumeRequest to Resume
        Resume resume = mapResumeDTOToResume(resumeRequest);
        resume.setUser(user);
        resume.setCreatedAt(LocalDateTime.now());
        // Save
        resume = resumeRepository.save(resume);

        return mapResumeToResumeDTO(resume);
    }

    /**
     * Find Resume by id
     * @param id
     * @return
     */
    @Override
    public ResumeDTO find(Long id) {
        Resume resume = resumeRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Resume doesn't exist with id "+ id));
        return mapResumeToResumeDTO(resume);
    }

    /**
     * Find Resumes
     * @param pageable
     * @return
     */
    @Override
    public List<ResumeDTO> findAll(Pageable pageable) {
        Page<Resume> resumes = resumeRepository.findAll(pageable);
        return resumes.map(resume -> mapResumeToResumeDTO(resume)).toList();
    }

    /**
     * Map resume to dto
     * @param resume
     * @return
     */
    private ResumeDTO mapResumeToResumeDTO(Resume resume){

        //Map Resume
        ResumeDTO resumeDTO = ResumeMapper.INSTANCE.mapResumeToResumeDTO(resume);

        //Map ContactInformation
        resumeDTO.setContact(ContactMapper.INSTANCE.mapContactInformationToContactInformationDTO(resume.getContactInformation()));

        // Map Skill
        List<Resume.Skill> skillList = resume.getSkillList();
        List<ResumeDTO.SkillDTO> skillDTOS = new ArrayList<>();
        for(var skill: skillList){
            skillDTOS.add(SkillMapper.INSTANCE.mapSkillToSkillDTO(skill));
        }
        resumeDTO.setSkills(skillDTOS);

        // Map Education
        List<Resume.Education> educationList = resume.getEducationList();
        List<ResumeDTO.EducationDTO> educationDTOS = new ArrayList<>();
        for(var education: educationList){
            educationDTOS.add(EducationMapper.INSTANCE.mapEducationToEducationDTO(education));
        }
        resumeDTO.setEducation(educationDTOS);

        // Map WorkExperience
        List<Resume.Experience> workExperienceList = resume.getWorkExperienceList();
        List<ResumeDTO.ExperienceDTO> workExperienceDTOS = new ArrayList<>();
        for(var workExperience: workExperienceList){
            workExperienceDTOS.add(ExperienceMapper.INSTANCE.mapWorkExperienceToWorkExperienceDTO(workExperience));
        }
        resumeDTO.setExperience(workExperienceDTOS);

        // Return
        return resumeDTO;
    }


    /**
     * Map dto to resume
     * @param resumeDTO
     * @return
     */
    private Resume mapResumeDTOToResume(ResumeDTO resumeDTO){

        //Map Resume
        Resume resume = ResumeMapper.INSTANCE.mapResumeDTOToResume(resumeDTO);

        //Map ContactInformation
        resume.setContactInformation(ContactMapper.INSTANCE.mapContactInformationDTOToContactInformation(resumeDTO.getContact()));
        resume.getContactInformation().setResume(resume);

        // Map Skill
        List<ResumeDTO.SkillDTO> skillDTOS = resumeDTO.getSkills();
        List<Resume.Skill> skillList = new ArrayList<>();
        for(var skillDTO: skillDTOS){
            Resume.Skill skill = SkillMapper.INSTANCE.mapSkillDTOToSkill(skillDTO);
            skill.setResume(resume);
            skillList.add(skill);
        }
        resume.setSkillList(skillList);

        // Map Education
        List<Resume.Education> educationList = new ArrayList<>();
        List<ResumeDTO.EducationDTO> educationDTOS =resumeDTO.getEducation();

        for(var educationDTO: educationDTOS){
            Resume.Education education = EducationMapper.INSTANCE.mapEducationDTOToEducation(educationDTO);
            education.setResume(resume);
            educationList.add(education);
        }
        resume.setEducationList(educationList);

        // Map WorkExperience
        List<Resume.Experience> workExperienceList = new ArrayList<>();
        List<ResumeDTO.ExperienceDTO> workExperienceDTOS = resumeDTO.getExperience();
        for(var workExperienceDTO: workExperienceDTOS){
            Resume.Experience workExperience = ExperienceMapper.INSTANCE.mapWorkExperienceDTOToWorkExperience(workExperienceDTO);
            workExperience.setResume(resume);
            workExperienceList.add(workExperience);
        }
        resume.setWorkExperienceList(workExperienceList);

        // Return
        return resume;
    }
}
