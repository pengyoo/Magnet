package com.pengyu.magnet.controller;

import com.pengyu.magnet.dto.TestPaperDTO;
import com.pengyu.magnet.dto.TestPaperGenerationRequest;
import com.pengyu.magnet.service.AIPaperGeneratorService;
import com.pengyu.magnet.service.TestPaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Test Paper Controller
 */
@RestController
@RequestMapping("/api/v1/assess/paper")
@RequiredArgsConstructor
public class TestPaperController {
    private final TestPaperService testPaperService;
    private final AIPaperGeneratorService paperGeneratorService;

    /**
     * Save Test Paper
     * @param testPaperDTO
     * @return
     */
    @PostMapping
    public TestPaperDTO save(@RequestBody TestPaperDTO testPaperDTO){
        return testPaperService.save(testPaperDTO);
    }

    /**
     * Generate Test Paper using AI
     * @param testPaperGenerationRequest
     * @return
     */
    @GetMapping("/generate")
    public TestPaperDTO generate(TestPaperGenerationRequest testPaperGenerationRequest) {
        return paperGeneratorService.generatePaper(testPaperGenerationRequest);
    }
}
