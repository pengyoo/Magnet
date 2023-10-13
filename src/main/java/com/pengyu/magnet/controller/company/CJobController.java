package com.pengyu.magnet.controller.company;

import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.dto.JobRequest;
import com.pengyu.magnet.dto.JobResponse;
import com.pengyu.magnet.service.compnay.JobService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cjobs")
public class CJobController {
    private final JobService jobService;

    /**
     * Add or Edit job info
     * @param jobRequest
     * @return
     */
    @RolesAllowed({CONSTANTS.ROLE_COMPANY, CONSTANTS.ROLE_ADMIN})
    @PostMapping
    public JobResponse save(@RequestBody JobRequest jobRequest){
        return jobService.save(jobRequest);
    }
    @RolesAllowed({CONSTANTS.ROLE_COMPANY, CONSTANTS.ROLE_ADMIN})
    @PatchMapping("/{id}")
    public JobResponse patch(@RequestBody JobRequest jobRequest){
        return jobService.save(jobRequest);
    }
}
