package com.pengyu.magnet.controller.company;

import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.dto.TestPaperDTO;
import com.pengyu.magnet.service.assessment.TestPaperService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ctests")
@RequiredArgsConstructor
public class CTestController {
    private final TestPaperService testPaperService;

    @PostMapping
    @RolesAllowed({CONSTANTS.ROLE_COMPANY})
    public TestPaperDTO save(@RequestBody TestPaperDTO testPaperDTO){
        return testPaperService.save(testPaperDTO);
    }

    @PatchMapping("/{id}")
    @RolesAllowed({CONSTANTS.ROLE_COMPANY})
    public TestPaperDTO patch(@RequestBody TestPaperDTO testPaperDTO){
        return testPaperService.save(testPaperDTO);
    }

    @DeleteMapping("/question/{testPaperId}/{questionId}")
    @RolesAllowed({CONSTANTS.ROLE_COMPANY})
    public void deleteQuestion(@PathVariable Long testPaperId, @PathVariable Long questionId){
        testPaperService.deleteQuestion(testPaperId, questionId);
    }


    @DeleteMapping("/{testPaperId}/")
    @RolesAllowed({CONSTANTS.ROLE_COMPANY})
    public void deleteTestPaper(@PathVariable Long testPaperId){
        testPaperService.deleteTestPaper(testPaperId);
    }


    @GetMapping
    @RolesAllowed({CONSTANTS.ROLE_COMPANY})
    public List<TestPaperDTO> findAll(@RequestParam(defaultValue = "0", required = false) Integer _start,
                                      @RequestParam(defaultValue = "10", required = false) Integer _end,
                                      @RequestParam(defaultValue = "id", required = false) String sortBy,
                                      @RequestParam(defaultValue = "desc", required = false) String order,
                                      HttpServletResponse response) {
        // process sort factor
        Sort sort = "desc".equals(order) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        // create pageable
        int pageSize = _end - _start;
        int page = _start / (pageSize - 1);
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        Page<TestPaperDTO> testPaperDTOPage = testPaperService.findAllByCurrentCompany(pageable);

        // Set Header
        String count = String.valueOf(testPaperDTOPage.getTotalElements());
        response.addHeader("x-total-count", count);
        response.addHeader("Access-Control-Expose-Headers", "x-total-count");

        return testPaperDTOPage.getContent();
    }

    @GetMapping("/{id}")
    @RolesAllowed({CONSTANTS.ROLE_COMPANY})
    public TestPaperDTO find(@PathVariable Long id){
        return testPaperService.find(id);
    }
}
