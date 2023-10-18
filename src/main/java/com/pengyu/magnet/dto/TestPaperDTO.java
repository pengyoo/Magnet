package com.pengyu.magnet.dto;

import com.pengyu.magnet.domain.assessment.Question;
import com.pengyu.magnet.domain.assessment.TestPaper;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TestPaperDTO {

    private Long id;

    private TestPaper.Type type;
    private Long jobId;
    private List<Question> questionList;

    private JobResponse job;

}
