package com.pengyu.magnet.service.match;

import com.pengyu.magnet.domain.match.JobInsights;

public interface JobRequirementsService {
    JobInsights save(JobInsights jobRequirements, Long jobId);
    JobInsights findByJobId(Long jobId);

}
