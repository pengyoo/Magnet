package com.pengyu.magnet.controller;

import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.domain.JobApplication;
import com.pengyu.magnet.dto.JobApplicationResponse;
import com.pengyu.magnet.service.JobApplicationService;
import jakarta.annotation.security.RolesAllowed;
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
     * @param page
     * @param pageSize
     * @param orderBy
     * @param order
     * @return list of JobResponse
     */
    @GetMapping()
    @RolesAllowed(value = {CONSTANTS.ROLE_JOB_SEEKER, CONSTANTS.ROLE_ADMIN})
    public List<JobApplicationResponse> findAll(@RequestParam(required = false, defaultValue = "0") int page,
                                         @RequestParam(required = false, defaultValue = "10") int pageSize,
                                         @RequestParam(required = false, defaultValue = "id") String orderBy,
                                         @RequestParam(required = false, defaultValue = "desc") String order,
                                         @RequestParam (required = false) Long userId
                                     ){
        // process sort factor
        Sort sort = "desc".equals(order) ? Sort.by(orderBy).descending() : Sort.by(orderBy).ascending();
        // create pageable
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return jobApplicationService.findAll(pageable, userId);
    }

    @PostMapping("/{id}")
    @RolesAllowed(value = {CONSTANTS.ROLE_COMPANY, CONSTANTS.ROLE_ADMIN})
    public void modifyState(@RequestParam JobApplication.Status status, @PathVariable Long id){
        jobApplicationService.modifyState(id, status);
    }

}
