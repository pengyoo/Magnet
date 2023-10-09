package com.pengyu.magnet.controller;

import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.dto.TestPaperDTO;
import com.pengyu.magnet.dto.TestPaperGenerationRequest;
import com.pengyu.magnet.service.assessment.AIPaperGeneratorService;
import com.pengyu.magnet.service.assessment.TestPaperService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletResponse;
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
@RequestMapping("/api/v1/papers")
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

    @GetMapping("/{id}")
    public TestPaperDTO find(@PathVariable Long id){
        return testPaperService.find(id);
    }

    @GetMapping
//    @RolesAllowed({CONSTANTS.ROLE_COMPANY, CONSTANTS.ROLE_ADMIN})
    public List<TestPaperDTO> findAll(@RequestParam(defaultValue = "0", required = false) Integer _start,
                                      @RequestParam(defaultValue = "10", required = false) Integer _end,
                                      @RequestParam(defaultValue = "id", required = false) String sort,
                                      @RequestParam(defaultValue = "desc", required = false) String order,
                                      @RequestParam(required = false) Long userId,
                                      HttpServletResponse response){
        // process sort factor
        Sort sortBy = "desc".equals(order) ? Sort.by(sort).descending() : Sort.by(sort).ascending();

        // create pageable
        int pageSize = _end - _start;
        int page = _start / (pageSize - 1);
        Pageable pageable = PageRequest.of(page, pageSize, sortBy);

        // Set Header
        String count = String.valueOf(testPaperService.count(userId));
        response.addHeader("x-total-count", count);
        response.addHeader("Access-Control-Expose-Headers", "x-total-count");

        return testPaperService.findAll(pageable, userId);
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
