package com.pengyu.magnet.service.match;

import com.pengyu.magnet.domain.Job;
import com.pengyu.magnet.domain.match.JobRequirements;
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
public class JobRequirementsServiceImpl implements JobRequirementsService {

    private final JobRequirementsRepository jobRequirementsRepository;

    private final JobRepository jobRepository;
    @Override
    public JobRequirements save(JobRequirements jobRequirements, Long jobId) {

        // Find Job
        Job job = jobRepository
                .findById(jobId)
                .orElseThrow(()-> new ResourceNotFoundException("No such job found with id "+ jobId));

        // Bind Job with jobRequirements
        jobRequirements.setJob(job);

        // Bind Skills with jobRequirements
        jobRequirements.getSkills().forEach(skill -> skill.setJobRequirements(jobRequirements));

        return jobRequirementsRepository.save(jobRequirements);
    }

    /**
     * Find by jobId
     * @param jobId
     * @return
     */
    @Override
    public JobRequirements findByJobId(Long jobId) {
        return jobRequirementsRepository
                .findByJobId(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("No such JobRequirements found with job id "+ jobId));
    }
}
