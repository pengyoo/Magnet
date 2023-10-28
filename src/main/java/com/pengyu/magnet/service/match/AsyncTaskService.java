package com.pengyu.magnet.service.match;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsyncTaskService {

    private final AIMatchService aiMatchService;


    @Async
    public void asyncExtractJobRequirements(Long jobId) {
         aiMatchService.extractJobInsights(jobId);
    }

    @Async
    public void asyncExtractResumeInsights(Long resumeId) {
        aiMatchService.extractResumeInsights(resumeId);
    }

    @Async
    public void asyncMatchJobAndResume(Long jobId, Long resumeId) {
        aiMatchService.match(jobId, resumeId);
    }
}
