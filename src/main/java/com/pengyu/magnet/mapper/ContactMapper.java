package com.pengyu.magnet.mapper;

import com.pengyu.magnet.domain.Resume;
import com.pengyu.magnet.dto.ResumeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactMapper {
    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    ResumeDTO.ContactInformationDTO mapContactInformationToContactInformationDTO(Resume.ContactInformation contactInformation);
    Resume.ContactInformation mapContactInformationDTOToContactInformation(ResumeDTO.ContactInformationDTO contactInformationDTO);
}
