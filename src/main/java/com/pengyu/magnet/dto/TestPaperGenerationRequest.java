package com.pengyu.magnet.dto;

import lombok.Data;

/**
 * Test Paper Generation Request DTO
 */
@Data
public class TestPaperGenerationRequest {
    private int generalNumber;
    private int languageNumber;
    private String language;
    private Long jobId;
}
