package com.pengyu.magnet.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pengyu.magnet.domain.assessment.AnswerSheet;
import com.pengyu.magnet.domain.assessment.Question;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AnswerDTO {
    private Long id;


    private String questionText;

    private String exampleAnswer;

    @NotBlank
    private String answer;

    private Long questionId;

    private Float score;

}
