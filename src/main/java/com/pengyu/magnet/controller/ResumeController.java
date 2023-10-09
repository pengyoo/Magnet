package com.pengyu.magnet.controller;

import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.dto.ResumeDTO;
import com.pengyu.magnet.service.resume.ResumeService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/resumes")
public class ResumeController {
    private final ResumeService resumeService;

    /**
     * Save resume info
     * @param resumeRequest
     * @return
     */
    @RolesAllowed(CONSTANTS.ROLE_JOB_SEEKER)
    @PostMapping("/save")
    public ResumeDTO save(@RequestBody ResumeDTO resumeRequest){
        return resumeService.save(resumeRequest);
    }

    /**
     * Find resume by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResumeDTO find(@PathVariable Long id){
        return resumeService.find(id);
    }

    /**
     * Find resumes
     * @param page
     * @param pageSize
     * @param orderBy
     * @param order
     * @return list of resumes
     */
    @GetMapping()
    public List<ResumeDTO> findAll(@RequestParam(required = false, defaultValue = "0") int page,
                                         @RequestParam(required = false, defaultValue = "10") int pageSize,
                                         @RequestParam(required = false, defaultValue = "id") String orderBy,
                                         @RequestParam(required = false, defaultValue = "desc") String order){
        // process sort factor
        Sort sort = "desc".equals(order) ? Sort.by(orderBy).descending() : Sort.by(orderBy).ascending();
        // create pageable
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return resumeService.findAll(pageable);
    }

}
