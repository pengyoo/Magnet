package com.pengyu.magnet.service.match;

import com.pengyu.magnet.domain.match.JobInsights;

public interface JobInsightsService {
    JobInsights save(JobInsights jobRequirements, Long jobId);
    JobInsights findByJobId(Long jobId);

}
