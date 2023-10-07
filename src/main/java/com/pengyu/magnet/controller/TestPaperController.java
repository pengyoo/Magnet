package com.pengyu.magnet.controller;

import com.pengyu.magnet.domain.assessment.TestPaper;
import com.pengyu.magnet.dto.TestPaperDTO;
import com.pengyu.magnet.service.TestPaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/assess/paper")
@RequiredArgsConstructor
public class TestPaperController {
    private final TestPaperService testPaperService;

    @PostMapping
    public TestPaperDTO save(@RequestBody TestPaperDTO testPaperDTO){
        return testPaperService.save(testPaperDTO);
    }
}
