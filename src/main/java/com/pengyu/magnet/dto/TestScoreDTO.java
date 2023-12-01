package com.pengyu.magnet.dto;

import lombok.Data;

import java.util.List;

@Data
public class TestScoreDTO {
    private Long id;
    private float score;
    private List<TestAnswerScoreDTO> answerScores;
}
