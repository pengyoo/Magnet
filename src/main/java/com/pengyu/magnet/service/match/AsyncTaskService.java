package com.pengyu.magnet.service.match;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * AsyncTaskService: execute background tasks
 */
@Service
@RequiredArgsConstructor
public class AsyncTaskService {

    private final AIMatchService aiMatchService;


    /**
     * Async method: extract Job Insights using AI
     * @param jobId
     */
    @Async
    public void asyncExtractJobInsights(Long jobId) {
         aiMatchService.extractJobInsights(jobId);
    }

    /**
     * Async method: extract Resume Insights using AI
     * @param resumeId
     */
    @Async
    public void asyncExtractResumeInsights(Long resumeId) {
        aiMatchService.extractResumeInsights(resumeId);
    }

    /**
     * Async method: Match ResumeInsights and JobInsights using AI
     * @param resumeId
     */
    @Async
    public void asyncMatchJobAndResume(Long jobId, Long resumeId) {
        aiMatchService.match(jobId, resumeId);
    }
}
