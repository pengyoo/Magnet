package com.pengyu.magnet.service.assessment;


import com.pengyu.magnet.dto.TestPaperDTO;
import com.pengyu.magnet.dto.TestPaperGenerationRequest;

public interface AIPaperGeneratorService {
    public TestPaperDTO generatePaper(TestPaperGenerationRequest testPaperGenerationRequest);
}
