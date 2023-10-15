package com.pengyu.magnet.controller.company;

import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.domain.JobApplication;
import com.pengyu.magnet.dto.JobApplicationResponse;
import com.pengyu.magnet.service.JobApplicationService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pengyu.magnet.utils.PageUtil.getPageable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/capplications")
public class CJobApplicationController {
    private final JobApplicationService jobApplicationService;

    /**
     * Find jobs
     * @param _start
     * @param _end
     * @param sort
     * @param order
     * @return list of JobResponse
     */
    @GetMapping()
    @RolesAllowed(value = {CONSTANTS.ROLE_COMPANY})
    public List<JobApplicationResponse> findAll(@RequestParam(defaultValue = "0", required = false) Integer _start,
                                                @RequestParam(defaultValue = "10", required = false) Integer _end,
                                                @RequestParam(defaultValue = "id", required = false) String sort,
                                                @RequestParam(defaultValue = "desc", required = false) String order,
                                                HttpServletResponse response
                                     ){


        Page<JobApplicationResponse> allByCurrentCompany = jobApplicationService.findAllByCurrentCompany(getPageable(_start, _end, sort, order));
        // Set Header
        response.addHeader("x-total-count", String.valueOf(allByCurrentCompany.getTotalElements()));
        response.addHeader("Access-Control-Expose-Headers", "x-total-count");

        return allByCurrentCompany.getContent();
    }

    @PostMapping("/{id}")
    @RolesAllowed(value = {CONSTANTS.ROLE_COMPANY})
    public void modifyState(@RequestParam JobApplication.Status status, @PathVariable Long id){
        jobApplicationService.modifyState(id, status);
    }

}
