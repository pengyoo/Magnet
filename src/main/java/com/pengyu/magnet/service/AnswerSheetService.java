package com.pengyu.magnet.service;

import com.pengyu.magnet.dto.AnswerDTO;
import com.pengyu.magnet.dto.AnswerSheetDTO;
import com.pengyu.magnet.dto.TestPaperDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnswerSheetService {
    AnswerSheetDTO save(AnswerSheetDTO answerSheetDTO);
    AnswerSheetDTO find(Long id);
    List<AnswerSheetDTO> findAllByCurrentUser(Pageable pageable);

    void saveAnswers(List<AnswerDTO> answerDTOs, Long answerSheetId);
}
