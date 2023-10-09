package com.pengyu.magnet.service.match;


import com.pengyu.magnet.domain.match.JobRequirements;
import com.pengyu.magnet.domain.match.MatchingIndex;
import com.pengyu.magnet.domain.match.ResumeInsights;
import com.pengyu.magnet.dto.MatchingIndexDTO;

/**
 * AI Test Paper Generator Service
 */
public interface AIMatchService {

    // Prompt Template


    /**
     * Call AI API, extract skills from job description
     *
     * @param jobId
     * @return
     */
//    public List<ResumeDTO.SkillDTO> extractSkills(Long jobId);

    /**
     * Call AI API, extract job requirements from job description
     *
     * @param jobId
     * @return
     */
    public JobRequirements extractJobRequirements(Long jobId);

    /**
     * Call AI API, extract Resume Characteristics from resume
     *
     * @param resumeId
     * @return
     */
    public ResumeInsights extractResumeInsights(Long resumeId);

    public MatchingIndexDTO match(Long jobId, Long resumeId);

    public MatchingIndexDTO find(Long jobId, Long resumeId);

}
