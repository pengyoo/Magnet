package com.pengyu.magnet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerSheetDTO {
    private Long id;

    @NotNull
    private Long paperId;
    private List<AnswerDTO> answers;

    @JsonProperty("user")
    private UserResponse userResponse;

    private Long invitationId;

    private String applicant;

    private float score;

}
