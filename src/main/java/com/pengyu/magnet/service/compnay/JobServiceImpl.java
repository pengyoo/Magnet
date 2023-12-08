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
import com.pengyu.magnet.service.ai.AsyncTaskService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
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
        if(job.getStatus() == null)
            job.setStatus(Job.Status.ACTIVE);

        // save
        job = jobRepository.save(job);

        // Async Task: AI extract Resume Insights
        Job finalJob = job;
        asynTaskService.asyncExtractJobInsights(job.getId());

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
        JobResponse jobResponse = mapToJobResponse(job);
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
            JobResponse jobResponse = mapToJobResponse(job);
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

        if(companyId != null) {

            Company company = companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException("No such company found with id " + companyId));
            jobs = jobRepository.findAllByCompany(pageable, company);
            return jobs.map(job -> {
                // map job to dto
                JobResponse jobResponse = mapToJobResponse(job);
                return jobResponse;
            }).toList();
        }

        // If comapnyId is not null, find jobs of this company
        else
            return findAll(pageable);


    }

    /**
     * Search By title
     * @param pageable
     * @param title_like
     * @return
     */
    @Override
    public List<JobResponse> findAll(Pageable pageable, String title_like) {
        Page<Job> jobs;

        if(StringUtils.isBlank(title_like))
            return findAll(pageable);
            // If comapnyId is not null, find jobs of this company
        else
            title_like = "%"+title_like+"%";
            jobs = jobRepository.findAllByTitleLike(pageable, title_like);

        return jobs.map(job -> {
            JobResponse jobResponse = mapToJobResponse(job);
            return jobResponse;
        }).toList();
    }

    @NotNull
    private static JobResponse mapToJobResponse(Job job) {
        // map job to dto
        JobResponse jobResponse = JobMapper.INSTANCE.mapJobToJobResponse(job);
        jobResponse.setCompanyData(CompanyMapper.INSTANCE.mapCompanyToCompanyResponse(job.getCompany()));

        // Set Company Logo Url
        if(job.getCompany().getUser().getHeadShotName() != null) {
            String url = "/images/%s/%s".formatted(job.getCompany().getUser().getEmail(), job.getCompany().getUser().getHeadShotName());
            jobResponse.getCompanyData().setLogoUrl(url);
        }
        return jobResponse;
    }

    @Override
    public long count(Long companyId) {
        if(companyId != null)
            return jobRepository.countByCompanyId(companyId);
        return jobRepository.count();
    }

    @Override
    public long count() {
        return jobRepository.count();
    }

    @Override
    public long count(String title) {
        if(StringUtils.isBlank(title)) {
            return jobRepository.count();
        }
        title = "%"+title+"%";
        return jobRepository.countByTitleLike(title);
    }

    /**
     * Count jobs of current company
     * @return
     */
    @Override
    public long countByCurrentCompany() {
        Company company = findCurrentCompany();

        return jobRepository.countByCompanyId(company.getId());
    }

    private Company findCurrentCompany() {
        // Get Current login user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        Company company = companyRepository
                .findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("You doesn't create company information."));
        return company;
    }


    /**
     * Find All Jobs of current company
     * @param pageable
     * @return
     */
    @Override
    public List<JobResponse> findAllByCurrentCompany(Pageable pageable) {
        Company company = findCurrentCompany();
        return jobRepository
                .findAllByCompany(pageable, company)
                .map(job ->
                    mapToJobResponse(job)
                ).toList();
    }

    @Override
    public void delete(Long id) {
        Job job = jobRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such job found with id "+id));
        job.setStatus(Job.Status.DELETED);
        jobRepository.save(job);
    }

    @Override
    public List<Long> getJobCounts() {
        return jobRepository.getJobCounts();
    }


}
