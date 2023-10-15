package com.pengyu.magnet.service.match;

import com.pengyu.magnet.domain.Job;
import com.pengyu.magnet.domain.match.JobInsights;
import com.pengyu.magnet.exception.ResourceNotFoundException;
import com.pengyu.magnet.repository.JobRepository;
import com.pengyu.magnet.repository.match.JobRequirementsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Job Requirement Service, used to operation generated Job Requirement by AI
 */
@Service
@RequiredArgsConstructor
public class JobInsightsServiceImpl implements JobInsightsService {

    private final JobRequirementsRepository jobRequirementsRepository;

    private final JobRepository jobRepository;
    @Override
    public JobInsights save(JobInsights jobRequirements, Long jobId) {

        // Find Job
        Job job = jobRepository
                .findById(jobId)
                .orElseThrow(()-> new ResourceNotFoundException("No such job found with id "+ jobId));

        // Bind Job with jobRequirements
        jobRequirements.setJob(job);

        // Bind Skills with jobRequirements
        jobRequirements.getSkills().forEach(skill -> skill.setJobInsights(jobRequirements));

        return jobRequirementsRepository.save(jobRequirements);
    }

    /**
     * Find by jobId
     * @param jobId
     * @return
     */
    @Override
    public JobInsights findByJobId(Long jobId) {
        return jobRequirementsRepository
                .findByJobId(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("No such JobRequirements found with jobId " +jobId));
    }
}
