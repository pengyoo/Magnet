package com.pengyu.magnet.controller;

import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.dto.AnswerSheetDTO;
import com.pengyu.magnet.dto.TestPaperDTO;
import com.pengyu.magnet.dto.TestPaperGenerationRequest;
import com.pengyu.magnet.service.AIPaperGeneratorService;
import com.pengyu.magnet.service.TestPaperService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @RolesAllowed({CONSTANTS.ROLE_COMPANY, CONSTANTS.ROLE_ADMIN})
    public TestPaperDTO save(@RequestBody TestPaperDTO testPaperDTO){
        return testPaperService.save(testPaperDTO);
    }

    @GetMapping
    @RolesAllowed({CONSTANTS.ROLE_COMPANY, CONSTANTS.ROLE_ADMIN})
    public List<TestPaperDTO> findAll(@RequestParam(required = false, defaultValue = "0") int page,
                                        @RequestParam(required = false, defaultValue = "10") int pageSize,
                                        @RequestParam(required = false, defaultValue = "id") String orderBy,
                                        @RequestParam(required = false, defaultValue = "desc") String order){
        // process sort factor
        Sort sort = "desc".equals(order) ? Sort.by(orderBy).descending() : Sort.by(orderBy).ascending();
        // create pageable
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return testPaperService.findAllByCurrentUser(pageable);
    }

    /**
     * Generate Test Paper using AI
     * @param testPaperGenerationRequest
     * @return
     */
    @GetMapping("/generate")
    @RolesAllowed({CONSTANTS.ROLE_COMPANY, CONSTANTS.ROLE_ADMIN})
    public TestPaperDTO generate(TestPaperGenerationRequest testPaperGenerationRequest) {
        return paperGeneratorService.generatePaper(testPaperGenerationRequest);
    }
}
