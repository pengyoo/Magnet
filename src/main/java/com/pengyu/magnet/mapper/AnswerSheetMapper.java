package com.pengyu.magnet.mapper;

import com.pengyu.magnet.domain.assessment.Answer;
import com.pengyu.magnet.domain.assessment.AnswerSheet;
import com.pengyu.magnet.dto.AnswerDTO;
import com.pengyu.magnet.dto.AnswerSheetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AnswerSheetMapper {
    AnswerSheetMapper INSTANCE = Mappers.getMapper(AnswerSheetMapper.class);

    AnswerSheet mapAnswerSheetDTOToAnswerSheet(AnswerSheetDTO answerSheetDTO);
    AnswerSheetDTO mapAnswerSheetToAnswerSheetDTO(AnswerSheet answerSheet);
}
