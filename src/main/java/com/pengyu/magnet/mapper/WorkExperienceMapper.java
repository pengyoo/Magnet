package com.pengyu.magnet.mapper;

import com.pengyu.magnet.domain.Resume;
import com.pengyu.magnet.dto.ResumeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WorkExperienceMapper {
    WorkExperienceMapper INSTANCE = Mappers.getMapper(WorkExperienceMapper.class);

    ResumeDTO.WorkExperienceDTO mapWorkExperienceToWorkExperienceDTO(Resume.WorkExperience workExperience);
    Resume.WorkExperience mapWorkExperienceDTOToWorkExperience(ResumeDTO.WorkExperienceDTO workExperienceDTO);
}
