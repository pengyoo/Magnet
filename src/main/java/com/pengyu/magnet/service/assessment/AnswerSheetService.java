package com.pengyu.magnet.service.assessment;

import com.pengyu.magnet.dto.AnswerDTO;
import com.pengyu.magnet.dto.AnswerSheetDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnswerSheetService {
    AnswerSheetDTO save(AnswerSheetDTO answerSheetDTO);
    AnswerSheetDTO find(Long id);
    List<AnswerSheetDTO> findAll(Pageable pageable, Long userId);

    void saveAnswers(List<AnswerDTO> answerDTOs, Long answerSheetId);

    long count(Long userId);
}
