package com.pengyu.magnet.service;

import com.pengyu.magnet.domain.*;
import com.pengyu.magnet.dto.CompanyResponse;
import com.pengyu.magnet.dto.JobApplicationResponse;
import com.pengyu.magnet.dto.JobResponse;
import com.pengyu.magnet.dto.UserResponse;
import com.pengyu.magnet.exception.ApiException;
import com.pengyu.magnet.exception.ResourceNotFoundException;
import com.pengyu.magnet.mapper.CompanyMapper;
import com.pengyu.magnet.mapper.JobApplicationMapper;
import com.pengyu.magnet.mapper.JobMapper;
import com.pengyu.magnet.mapper.UserMapper;
import com.pengyu.magnet.repository.*;
import com.pengyu.magnet.service.match.AsyncTaskService;
import com.pengyu.magnet.service.resume.ResumeService;
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
    private final CompanyRepository companyRepository;

    private final ResumeService resumeService;

    /**
     * Apply a job
     * @param jobId
     * @return
     */
    @Override
    public JobApplicationResponse apply(Long jobId) {
        User user = getCurrentUser();

        // Check if the user applied this job
        JobApplication ja = jobApplicationRepository.findByUserIdAndJobId(user.getId(), jobId);
        if(ja != null) {
            throw new ApiException("Sorry, you've already applied for this job!");
        }

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
                .status(JobApplication.Status.PENDING)
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
     * Find Job Application by current login user
     * @param pageable
     * @return
     */
    @Override
    public List<JobApplicationResponse> findAllByCurrentUser(Pageable pageable) {
        // Get Current login user
        User user = getCurrentUser();

        return findAll(pageable, user.getId());
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


    /**
     * Get Job count
     * @return
     */
    @Override
    public long count() {
        return jobApplicationRepository.count();
    }

    @Override
    public Page<JobApplicationResponse> findAllByCurrentCompany(Pageable pageable) {
        Company company = getCurrentCompany();
        return jobApplicationRepository.findAllByCompany(pageable, company)
                .map(jobApplication -> mapJobApplicationToJobApplicationResponse(jobApplication));
    }

    @Override
    public long countByCurrentUser() {
        // Get Current login user
        User user = getCurrentUser();
        return jobApplicationRepository.countByUserId(user.getId());
    }


    /**
     * Get Login User
     * @return
     */

    private User getCurrentUser() {
        // Get Current login user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        return user;
    }

    /**
     * Get login Company
     * @return
     */
    private Company getCurrentCompany() {
        User user = getCurrentUser();
        Company company = companyRepository
                .findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No such company found with user id " + user.getId()));
        return company;
    }

    /**
     * Map jobApplication to JobApplicationResponse
     * @param jobApplication
     * @return
     */
    private JobApplicationResponse mapJobApplicationToJobApplicationResponse(JobApplication jobApplication){

        // Set Job
        CompanyResponse companyResponse = CompanyMapper.INSTANCE.mapCompanyToCompanyResponse(jobApplication.getJob().getCompany());
        JobResponse jobResponse = JobMapper.INSTANCE.mapJobToJobResponse(jobApplication.getJob());
        jobResponse.setCompanyData(companyResponse);
        JobApplicationResponse jobApplicationResponse = JobApplicationMapper.INSTANCE.mapJobApplicationToJobApplicationResponse(jobApplication);
        jobApplicationResponse.setJobData(jobResponse);

        // Set User
        UserResponse userResponse = UserMapper.INSTANCE.mapUserToUserResponse(jobApplication.getUser());
        jobApplicationResponse.setUserData(userResponse);

        // Set Resume
        jobApplicationResponse.setResume(resumeService.findResumeByUserId(jobApplication.getUser().getId()));

        return jobApplicationResponse;
    }
}
