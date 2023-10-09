package com.pengyu.magnet.controller;

import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.domain.JobApplication;
import com.pengyu.magnet.dto.JobApplicationResponse;
import com.pengyu.magnet.service.JobApplicationService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/applications")
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;

    /**
     * Add or Edit job info
     * @param jobId
     * @return
     */
    @RolesAllowed(value = {CONSTANTS.ROLE_JOB_SEEKER})
    @PostMapping("/apply")
    public JobApplicationResponse apply(Long jobId){
        return jobApplicationService.apply(jobId);
    }

    /**
     * Find job by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public JobApplicationResponse find(@PathVariable Long id){
        return jobApplicationService.find(id);
    }

    /**
     * Find jobs
     * @param _start
     * @param _end
     * @param sort
     * @param order
     * @return list of JobResponse
     */
    @GetMapping()
//    @RolesAllowed(value = {CONSTANTS.ROLE_JOB_SEEKER, CONSTANTS.ROLE_ADMIN})
    public List<JobApplicationResponse> findAll(@RequestParam(defaultValue = "0", required = false) Integer _start,
                                                @RequestParam(defaultValue = "10", required = false) Integer _end,
                                                @RequestParam(defaultValue = "id", required = false) String sort,
                                                @RequestParam(defaultValue = "desc", required = false) String order,
                                                HttpServletResponse response,
                                         @RequestParam (required = false) Long userId
                                     ){
        // process sort factor
        Sort sortBy = "desc".equals(order) ? Sort.by(sort).descending() : Sort.by(sort).ascending();

        // create pageable
        int pageSize = _end - _start;
        int page = _start / (pageSize - 1);
        Pageable pageable = PageRequest.of(page, pageSize, sortBy);

        // Set Header
        String count = String.valueOf(jobApplicationService.count(userId));
        response.addHeader("x-total-count", count);
        response.addHeader("Access-Control-Expose-Headers", "x-total-count");

        return jobApplicationService.findAll(pageable, userId);
    }

    @PostMapping("/{id}")
    @RolesAllowed(value = {CONSTANTS.ROLE_COMPANY, CONSTANTS.ROLE_ADMIN})
    public void modifyState(@RequestParam JobApplication.Status status, @PathVariable Long id){
        jobApplicationService.modifyState(id, status);
    }

}
