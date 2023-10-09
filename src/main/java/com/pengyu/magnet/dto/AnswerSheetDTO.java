package com.pengyu.magnet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AnswerSheetDTO {
    private Long id;
    private Long paperId;
    private List<AnswerDTO> answers;

    @JsonProperty("user")
    private UserResponse userResponse;

}
