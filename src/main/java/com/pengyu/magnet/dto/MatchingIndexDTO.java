package com.pengyu.magnet.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Matching Index between Job and Resume
 */
@Data
public class MatchingIndexDTO {
    private Long id;
    private float degree;
    private float major;
    private float experience;
    private float skill;
    private float language;
    private float overall;

    @JsonProperty("job")
    private JobResponse jobResponse;
    @JsonProperty("resume")
    private ResumeDTO resumeDTO;
}
