package com.pengyu.magnet.dto;


import lombok.Data;

/**
 * Matching Index between Job and Resume
 */
@Data
public class MatchingIndexDTO {
    private float degree;
    private float major;
    private float experience;
    private float skill;
    private float language;
    private float overall;

    private JobResponse jobResponse;
    private ResumeDTO resumeDTO;
}
