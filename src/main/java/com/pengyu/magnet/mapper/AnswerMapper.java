package com.pengyu.magnet.mapper;

import com.pengyu.magnet.domain.Company;
import com.pengyu.magnet.domain.assessment.Answer;
import com.pengyu.magnet.dto.AnswerDTO;
import com.pengyu.magnet.dto.CompanyRequest;
import com.pengyu.magnet.dto.CompanyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AnswerMapper {
    AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);

    Answer mapAnswerDTOToAnswer(AnswerDTO answerDTO);
    AnswerDTO mapAnswerToAnswerDTO(Answer answer);
}
