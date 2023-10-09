package com.pengyu.magnet.controller;


import com.pengyu.magnet.domain.match.JobRequirements;
import com.pengyu.magnet.domain.match.MatchingIndex;
import com.pengyu.magnet.domain.match.ResumeInsights;
import com.pengyu.magnet.dto.MatchingIndexDTO;
import com.pengyu.magnet.service.match.AIMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * AI Match Controller, used to match Resume and Job using AI
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/aimatch")
public class AIMatchController {

    private final AIMatchService aiMatchService;

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
    public JobRequirements extractJob(@RequestParam Long jobId) {
        return aiMatchService.extractJobRequirements(jobId);
    }

    /**
     * Extract Resume to ResumeInsights using AI
     * @param resumeId
     * @return
     */
    @GetMapping("/extract_resume")
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
