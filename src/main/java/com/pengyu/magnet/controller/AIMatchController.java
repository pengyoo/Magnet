package com.pengyu.magnet.controller;


import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.domain.match.JobInsights;
import com.pengyu.magnet.domain.match.ResumeInsights;
import com.pengyu.magnet.dto.MatchingIndexDTO;
import com.pengyu.magnet.service.ai.AIService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * AI Match Controller, used to match Resume and Job using AI
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/aimatch")
public class AIMatchController {

    private final AIService aiMatchService;

//    @GetMapping("/extract_skills")
//    public List<ResumeDTO.SkillDTO> extractSkill(@RequestParam Long jobId) {
//        return aiMatchService.extractSkills(jobId);
//    }

    /**
     * Extract Job to JobRequirements using AI
     * @param jobId
     * @return
     */
    @GetMapping("/extract_job")
    @RolesAllowed({CONSTANTS.ROLE_ADMIN})
    public JobInsights extractJob(@RequestParam Long jobId) {
        return aiMatchService.extractJobInsights(jobId);
    }

    /**
     * Extract Resume to ResumeInsights using AI
     * @param resumeId
     * @return
     */
    @GetMapping("/extract_resume")
    @RolesAllowed({CONSTANTS.ROLE_ADMIN})
    public ResumeInsights extractResume(@RequestParam Long resumeId) {
        return aiMatchService.extractResumeInsights(resumeId);
    }

    /**
     * Match job and resume, return matching result
     * @param jobId
     * @param resumeId
     * @return
     */
    @GetMapping("/match")
    @RolesAllowed({CONSTANTS.ROLE_ADMIN})
    public MatchingIndexDTO match(@RequestParam Long jobId, @RequestParam Long resumeId) {
        return aiMatchService.match(jobId, resumeId);
    }

    /**
     * Find Matching Index
     * @param jobId
     * @param resumeId
     * @return
     */
    @GetMapping("/find")
    public MatchingIndexDTO find(@RequestParam Long jobId, @RequestParam Long resumeId) {
        return aiMatchService.find(jobId, resumeId);
    }



}
