package com.pengyu.magnet.mapper;

import com.pengyu.magnet.domain.Resume;
import com.pengyu.magnet.dto.ResumeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    ResumeDTO.ProjectDTO mapProjectToProjectDTO(Resume.Project project);
    Resume.Project mapProjectDTOToProject(ResumeDTO.ProjectDTO projectDTO);
}
