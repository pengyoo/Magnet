package com.pengyu.magnet.service;

import com.pengyu.magnet.domain.Job;
import com.pengyu.magnet.domain.JobApplication;
import com.pengyu.magnet.domain.Resume;
import com.pengyu.magnet.domain.User;
import com.pengyu.magnet.dto.JobApplicationResponse;
import com.pengyu.magnet.dto.JobResponse;
import com.pengyu.magnet.dto.UserResponse;
import com.pengyu.magnet.exception.ResourceNotFoundException;
import com.pengyu.magnet.mapper.JobApplicationMapper;
import com.pengyu.magnet.mapper.JobMapper;
import com.pengyu.magnet.mapper.UserMapper;
import com.pengyu.magnet.repository.JobApplicationRepository;
import com.pengyu.magnet.repository.JobRepository;
import com.pengyu.magnet.repository.ResumeRepository;
import com.pengyu.magnet.repository.UserRepository;
import com.pengyu.magnet.service.match.AsyncTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Job Application business layer
 */
@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;

    private final JobRepository jobRepository;

    private final ResumeRepository resumeRepository;

    private final AsyncTaskService asynTaskService;

    /**
     * Apply a job
     * @param jobId
     * @return
     */
    @Override
    public JobApplicationResponse apply(Long jobId) {
        // Get Current login user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);

        // Get Resume of current User
        Resume resume = resumeRepository
                .findByUserId(user.getId())
                .orElseThrow(()-> new ResourceNotFoundException("You have not create your CV, please create CV before applying a position"));

        // Get Job info
        Job job = jobRepository
                .findById(jobId)
                .orElseThrow(()->new ResourceNotFoundException("The position you are applying doesn't exit"));

        // Check if job status
        if(job.getStatus() != Job.Status.ACTIVE) {
            throw new AccessDeniedException("This position is not available anymore");
        }

        // Build jobApplication
        JobApplication jobApplication = JobApplication.builder()
                .job(job)
                .appliedDate(LocalDateTime.now())
                .user(user)
                .status(JobApplication.Status.PENDING_REVIEW)
                .build();

        // Save
        jobApplication = jobApplicationRepository.save(jobApplication);

        // Async Task: AI match job and resume
        asynTaskService.asyncMatchJobAndResume(jobId, resume.getId());

        return mapJobApplicationToJobApplicationResponse(jobApplication);
    }

    /**
     * Find Job Application by id
     * @param id
     * @return
     */
    @Override
    public JobApplicationResponse find(Long id) {
        JobApplication jobApplication = jobApplicationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There is no Job Application with id " + id));
        return mapJobApplicationToJobApplicationResponse(jobApplication);
    }

    /**
     * Find Job Applications of a user
     * @param pageable
     * @param userId
     * @return
     */
    @Override
    public List<JobApplicationResponse> findAll(Pageable pageable, Long userId) {

        if(userId != null) {
            Page<JobApplication> jobApplications = jobApplicationRepository.findByUserId(pageable, userId);
            return jobApplications.map(jobApplication -> mapJobApplicationToJobApplicationResponse(jobApplication)).toList();
        }

        Page<JobApplication> jobApplications = jobApplicationRepository.findAll(pageable);
        return jobApplications.map(jobApplication -> mapJobApplicationToJobApplicationResponse(jobApplication)).toList();
    }

    /**
     * Change the state of job application
     * @param id
     * @param status
     */
    @Override
    public void modifyState(Long id, JobApplication.Status status) {
        JobApplication jobApplication =
                jobApplicationRepository
                        .findById(id)
                        .orElseThrow(()-> new ResourceNotFoundException("There is no Job Application with id " + id));

        // Get Current login user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Check if this application belongs to current user or if the user is ADMIN
        if(User.Role.ADMIN != jobApplication.getUser().getRole() &&
                !email.equals(jobApplication.getJob().getCompany().getUser().getEmail())) {
            throw new AccessDeniedException("You don't have the permission to modify the status of this job application");
        }

        jobApplication.setStatus(status);
        jobApplicationRepository.save(jobApplication);
    }

    @Override
    public long count(Long userId) {
        if(userId != null)
            return jobApplicationRepository.countByUserId(userId);
        return jobApplicationRepository.count();
    }

    /**
     * Map jobApplication to JobApplicationResponse
     * @param jobApplication
     * @return
     */
    private JobApplicationResponse mapJobApplicationToJobApplicationResponse(JobApplication jobApplication){
        JobResponse jobResponse = JobMapper.INSTANCE.mapJobToJobResponse(jobApplication.getJob());
        UserResponse userResponse = UserMapper.INSTANCE.mapUserToUserResponse(jobApplication.getUser());
        JobApplicationResponse jobApplicationResponse = JobApplicationMapper.INSTANCE.mapJobApplicationToJobApplicationResponse(jobApplication);
        jobApplicationResponse.setJobData(jobResponse);
        jobApplicationResponse.setUserData(userResponse);
        return jobApplicationResponse;
    }
}
