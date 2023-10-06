package com.pengyu.magnet.mapper;

import com.pengyu.magnet.domain.Resume;
import com.pengyu.magnet.dto.ResumeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExperienceMapper {
    ExperienceMapper INSTANCE = Mappers.getMapper(ExperienceMapper.class);

    ResumeDTO.ExperienceDTO mapWorkExperienceToWorkExperienceDTO(Resume.Experience workExperience);
    Resume.Experience mapWorkExperienceDTOToWorkExperience(ResumeDTO.ExperienceDTO workExperienceDTO);
}
