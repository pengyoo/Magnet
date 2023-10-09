package com.pengyu.magnet.controller;

import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.dto.JobRequest;
import com.pengyu.magnet.dto.JobResponse;
import com.pengyu.magnet.service.compnay.JobService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/jobs")
public class JobController {
    private final JobService jobService;

    /**
     * Add or Edit job info
     * @param jobRequest
     * @return
     */
    @RolesAllowed(CONSTANTS.ROLE_COMPANY)
    @PostMapping("/save")
    public JobResponse save(@RequestBody JobRequest jobRequest){
        return jobService.save(jobRequest);
    }

    /**
     * Find job by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public JobResponse find(@PathVariable Long id){
        return jobService.find(id);
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
    public List<JobResponse> findAll(@RequestParam(required = false, defaultValue = "0") int page,
                                         @RequestParam(required = false, defaultValue = "10") int pageSize,
                                         @RequestParam(required = false, defaultValue = "id") String orderBy,
                                         @RequestParam(required = false, defaultValue = "desc") String order,
                                         @RequestParam (required = false) Long companyId
                                     ){
        // process sort factor
        Sort sort = "desc".equals(order) ? Sort.by(orderBy).descending() : Sort.by(orderBy).ascending();
        // create pageable
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return jobService.findAll(pageable, companyId);
    }

}
