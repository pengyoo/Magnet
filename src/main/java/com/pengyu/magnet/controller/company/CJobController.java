package com.pengyu.magnet.controller.company;

import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.dto.JobRequest;
import com.pengyu.magnet.dto.JobResponse;
import com.pengyu.magnet.service.compnay.JobService;
import com.pengyu.magnet.utils.PageUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @RolesAllowed({CONSTANTS.ROLE_COMPANY})
    @PostMapping
    public JobResponse save(@Valid @RequestBody JobRequest jobRequest){
        return jobService.save(jobRequest);
    }
    @RolesAllowed({CONSTANTS.ROLE_COMPANY})
    @PatchMapping("/{id}")
    public JobResponse patch(@Valid @RequestBody JobRequest jobRequest){
        return jobService.save(jobRequest);
    }


    /**
     * Find All job of current company
     * @param _start
     * @param _end
     * @param sortBy
     * @param order
     * @param response
     * @return
     */
    @GetMapping
    @RolesAllowed({CONSTANTS.ROLE_COMPANY})
    public List<JobResponse> findAll(@RequestParam(defaultValue = "0", required = false) Integer _start,
                                     @RequestParam(defaultValue = "10", required = false) Integer _end,
                                     @RequestParam(defaultValue = "id", required = false) String sortBy,
                                     @RequestParam(defaultValue = "desc", required = false) String order,
                                     HttpServletResponse response
    ){

        Pageable pageable = PageUtil.getPageable(_start, _end, sortBy, order);

        // Set Header
        String count = String.valueOf(jobService.countByCurrentCompany());
        response.addHeader("x-total-count", count);
        response.addHeader("Access-Control-Expose-Headers", "x-total-count");

        return jobService.findAllByCurrentCompany(pageable);
    }

    /**
     * Find job by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @RolesAllowed({CONSTANTS.ROLE_COMPANY})
    public JobResponse find(@PathVariable Long id){
        return jobService.find(id);
    }

    /**
     * Find job by id
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @RolesAllowed({CONSTANTS.ROLE_COMPANY})
    public void delete(@PathVariable Long id){
        jobService.delete(id);
    }


}
