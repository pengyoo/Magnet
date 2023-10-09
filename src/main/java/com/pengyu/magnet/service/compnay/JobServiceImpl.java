package com.pengyu.magnet.service.compnay;

import com.pengyu.magnet.domain.Company;
import com.pengyu.magnet.domain.Job;
import com.pengyu.magnet.domain.User;
import com.pengyu.magnet.dto.JobRequest;
import com.pengyu.magnet.dto.JobResponse;
import com.pengyu.magnet.exception.ResourceNotFoundException;
import com.pengyu.magnet.mapper.CompanyMapper;
import com.pengyu.magnet.mapper.JobMapper;
import com.pengyu.magnet.repository.CompanyRepository;
import com.pengyu.magnet.repository.JobRepository;
import com.pengyu.magnet.repository.UserRepository;
import com.pengyu.magnet.service.match.AsyncTaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    private final AsyncTaskService asynTaskService;

    /**
     * Add or Edit Job
     * @param jobRequest
     * @return
     */
    @Override
    public JobResponse save(JobRequest jobRequest) {
        // Get Current login user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);

        // Get company of current user
        Company company = companyRepository
                .findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Please fill company information before post jobs!"));

        // map job dto to job
        Job job = JobMapper.INSTANCE.mapJobRequestToJob(jobRequest);

        // bind company
        job.setCompany(company);

        // Set default values
        job.setCreatedAt(LocalDateTime.now());

        // Set default status: Active
        job.setStatus(Job.Status.ACTIVE);

        // save
        job = jobRepository.save(job);

        // Async Task: AI extract Resume Insights
        Job finalJob = job;
        asynTaskService.asyncExtractJobRequirements(job.getId());

        // map job to dto
        JobResponse jobResponse = JobMapper.INSTANCE.mapJobToJobResponse(job);
        jobResponse.setCompanyData(CompanyMapper.INSTANCE.mapCompanyToCompanyResponse(company));
        return jobResponse;
    }

    /**
     * Find Job by id
     * @param id
     * @return
     */
    @Override
    public JobResponse find(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Job doesn't exit with id "+id));
        // map job to dto
        JobResponse jobResponse = JobMapper.INSTANCE.mapJobToJobResponse(job);
        jobResponse.setCompanyData(CompanyMapper.INSTANCE.mapCompanyToCompanyResponse(job.getCompany()));
        return jobResponse;
    }

    /**
     * Find Jobs by pageable
     * @param pageable
     * @return
     */
    @Override
    public List<JobResponse> findAll(Pageable pageable) {
        Page<Job> jobs = jobRepository.findAll(pageable);
        return jobs.map(job -> {
            // map job to dto
            JobResponse jobResponse = JobMapper.INSTANCE.mapJobToJobResponse(job);
            jobResponse.setCompanyData(CompanyMapper.INSTANCE.mapCompanyToCompanyResponse(job.getCompany()));
            return jobResponse;
        }).toList();
    }

    /**
     * Find companies by companyId
     * @param pageable
     * @param companyId
     * @return
     */
    @Override
    public List<JobResponse> findAll(Pageable pageable, Long companyId) {
        Page<Job> jobs;

        if(companyId == null)
            return findAll(pageable);

        // If comapnyId is not null, find jobs of this company
        else
            jobs =  jobRepository.findAllByCompanyId(pageable, companyId);

        return jobs.map(job -> {
            // map job to dto
            JobResponse jobResponse = JobMapper.INSTANCE.mapJobToJobResponse(job);
            return jobResponse;
        }).toList();
    }
}
