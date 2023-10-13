package com.pengyu.magnet.controller.jobseeker;


import com.pengyu.magnet.dto.JobResponse;
import com.pengyu.magnet.service.compnay.JobService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("jobSeekerJobController")
@RequiredArgsConstructor
@RequestMapping("/api/v1/explore_jobs")
public class JobController {
    private final JobService jobService;

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
     * @param _start
     * @param _end
     * @param sortBy
     * @param order
     * @param title_like
     * @return list of JobResponse
     */
    @GetMapping()
    public List<JobResponse> findAll(@RequestParam(defaultValue = "0", required = false) Integer _start,
                                     @RequestParam(defaultValue = "10", required = false) Integer _end,
                                     @RequestParam(defaultValue = "id", required = false) String sortBy,
                                     @RequestParam(defaultValue = "desc", required = false) String order,
                                     @RequestParam(required = false) String title_like,
                                     HttpServletResponse response
    ){
        // process sort factor
        Sort sort = "desc".equals(order) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        // create pageable
        int pageSize = _end - _start;
        int page = _start / (pageSize - 1);
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        // Set Header
        String count = String.valueOf(jobService.count(title_like));
        response.addHeader("x-total-count", count);
        response.addHeader("Access-Control-Expose-Headers", "x-total-count");

        return jobService.findAll(pageable, title_like);
    }

}
