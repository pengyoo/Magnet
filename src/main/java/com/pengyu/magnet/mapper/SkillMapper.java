package com.pengyu.magnet.mapper;

import com.pengyu.magnet.domain.Resume;
import com.pengyu.magnet.dto.ResumeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SkillMapper {
    SkillMapper INSTANCE = Mappers.getMapper(SkillMapper.class);

    ResumeDTO.SkillDTO mapSkillToSkillDTO(Resume.Skill skill);
    Resume.Skill mapSkillDTOToSkill(ResumeDTO.SkillDTO skillDTO);
}
