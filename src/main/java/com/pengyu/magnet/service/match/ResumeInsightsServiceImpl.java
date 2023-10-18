package com.pengyu.magnet.service.match;

import com.pengyu.magnet.domain.Resume;
import com.pengyu.magnet.domain.match.ResumeInsights;
import com.pengyu.magnet.exception.ResourceNotFoundException;
import com.pengyu.magnet.repository.ResumeRepository;
import com.pengyu.magnet.repository.match.ResumeInsightsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Resume Insights Service, used to operation generated Resume Insights by AI
 */
@Service
@RequiredArgsConstructor
public class ResumeInsightsServiceImpl implements ResumeInsightsService {

    private final ResumeInsightsRepository resumeInsightsRepository;

    private final ResumeRepository resumeRepository;
    @Override
    public ResumeInsights save(ResumeInsights resumeInsights, Long resumeId) {

        // Find Resume
        Resume resume = resumeRepository
                .findById(resumeId)
                .orElseThrow(()-> new ResourceNotFoundException("No such resume found with id "+ resumeId));

        // Bind Resume with ResumeInsights
        resumeInsights.setResume(resume);

        // Bind Skills with jobRequirements
        resumeInsights.getSkills().forEach(skill -> skill.setResumeInsights(resumeInsights));


        return resumeInsightsRepository.save(resumeInsights);
    }

    @Override
    public ResumeInsights findByResumeId(Long resumeId) {
        return resumeInsightsRepository
                .findByResumeId(resumeId)
                .orElse(null);

    }
}
