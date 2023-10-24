package com.pengyu.magnet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pengyu.magnet.domain.assessment.TestInvitation;
import com.pengyu.magnet.domain.assessment.TestPaper;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Test Invitation domain
 */
@Data
public class TestInvitationDTO {

    private Long id;
    private String applicant;
    private String jobTitle;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private TestInvitation.Status status;


}
