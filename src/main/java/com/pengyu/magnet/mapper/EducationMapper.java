package com.pengyu.magnet.mapper;

import com.pengyu.magnet.domain.Resume;
import com.pengyu.magnet.dto.ResumeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EducationMapper {
    EducationMapper INSTANCE = Mappers.getMapper(EducationMapper.class);

    ResumeDTO.EducationDTO mapEducationToEducationDTO(Resume.Education education);
    Resume.Education mapEducationDTOToEducation(ResumeDTO.EducationDTO educationDTO);
}
