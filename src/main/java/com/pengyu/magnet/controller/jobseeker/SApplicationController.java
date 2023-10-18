package com.pengyu.magnet.controller.jobseeker;

import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.dto.JobApplicationDTO;
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
@RequestMapping("/api/v1/sapplications")
public class SApplicationController {
    private final JobApplicationService jobApplicationService;

    /**
     * Add or Edit job info
     * @param jobId
     * @return
     */
    @RolesAllowed(value = {CONSTANTS.ROLE_JOB_SEEKER})
    @PostMapping("/apply/{jobId}")
    public JobApplicationDTO apply(@PathVariable Long jobId){
        return jobApplicationService.apply(jobId);
    }

    /**
     * Find job by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @RolesAllowed(value = {CONSTANTS.ROLE_JOB_SEEKER})
    public JobApplicationDTO find(@PathVariable Long id){
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
    @RolesAllowed(value = {CONSTANTS.ROLE_JOB_SEEKER})
    public List<JobApplicationDTO> findAll(@RequestParam(defaultValue = "0", required = false) Integer _start,
                                           @RequestParam(defaultValue = "10", required = false) Integer _end,
                                           @RequestParam(defaultValue = "id", required = false) String sort,
                                           @RequestParam(defaultValue = "desc", required = false) String order,
                                           HttpServletResponse response
                                     ){
        // process sort factor
        Sort sortBy = "desc".equals(order) ? Sort.by(sort).descending() : Sort.by(sort).ascending();

        // create pageable
        int pageSize = _end - _start;
        int page = _start / (pageSize - 1);
        Pageable pageable = PageRequest.of(page, pageSize, sortBy);

        // Set Header
        String count = String.valueOf(jobApplicationService.countByCurrentUser());
        response.addHeader("x-total-count", count);
        response.addHeader("Access-Control-Expose-Headers", "x-total-count");

        return jobApplicationService.findAllByCurrentUser(pageable);
    }
}
