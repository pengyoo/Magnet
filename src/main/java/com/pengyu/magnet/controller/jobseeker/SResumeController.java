package com.pengyu.magnet.controller.jobseeker;

import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.dto.ResumeDTO;
import com.pengyu.magnet.service.resume.ResumeService;
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
@RequestMapping("/api/v1/sresumes")
public class SResumeController {
    private final ResumeService resumeService;

    /**
     * Add resume info
     * @param resumeRequest
     * @return
     */
    @RolesAllowed({CONSTANTS.ROLE_JOB_SEEKER})
    @PostMapping
    public ResumeDTO save(@RequestBody ResumeDTO resumeRequest){
        return resumeService.save(resumeRequest);
    }

    /**
     * Find resume by id
     * @return
     */
    @GetMapping
    @RolesAllowed({CONSTANTS.ROLE_JOB_SEEKER})
    public ResumeDTO findMy(){
        return resumeService.findMyResume();
    }


    /**
     * Edit resume info
     * @param resumeRequest
     * @return
     */
    @RolesAllowed({CONSTANTS.ROLE_JOB_SEEKER})
    @PatchMapping("/{id}")
    public ResumeDTO patch(@RequestBody ResumeDTO resumeRequest){
        return resumeService.save(resumeRequest);
    }
}
