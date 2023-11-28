package com.pengyu.magnet.controller.jobseeker;

import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.dto.ResumeDTO;
import com.pengyu.magnet.service.resume.ResumeService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public ResumeDTO save(@Valid @RequestBody ResumeDTO resumeRequest){
        return resumeService.save(resumeRequest);
    }

    /**
     * Find resume by id
     * @return
     */
    @GetMapping
    @RolesAllowed({CONSTANTS.ROLE_JOB_SEEKER})
    public ResumeDTO findMy(){
        return resumeService.findMyResumeDTO();
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

    @DeleteMapping("/skill/{id}")
    @RolesAllowed({CONSTANTS.ROLE_JOB_SEEKER})
    public void deleteSkill(@PathVariable Long id) {
        resumeService.deleteSkill(id);
    }

    @DeleteMapping("/education/{id}")
    @RolesAllowed({CONSTANTS.ROLE_JOB_SEEKER})
    public void deleteEducation(@PathVariable Long id) {
        resumeService.deleteEducation(id);
    }

    @DeleteMapping("/experience/{id}")
    @RolesAllowed({CONSTANTS.ROLE_JOB_SEEKER})
    public void deleteExperience(@PathVariable Long id) {
        resumeService.deleteExperience(id);
    }

    @DeleteMapping("/project/{id}")
    @RolesAllowed({CONSTANTS.ROLE_JOB_SEEKER})
    public void deleteProject(@PathVariable Long id) {
        resumeService.deleteProject(id);
    }

}

