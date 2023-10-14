package com.pengyu.magnet.service.match;

import com.pengyu.magnet.domain.match.ResumeInsights;

public interface ResumeInsightsService {
     ResumeInsights save(ResumeInsights resumeInsights, Long resumeId);
     ResumeInsights findByResumeId(Long resumeId);
}
