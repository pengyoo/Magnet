package com.pengyu.magnet.service.match;

import com.pengyu.magnet.domain.match.JobRequirements;

public interface JobRequirementsService {
    JobRequirements save(JobRequirements jobRequirements, Long jobId);
    JobRequirements findByJobId(Long jobId);

}
