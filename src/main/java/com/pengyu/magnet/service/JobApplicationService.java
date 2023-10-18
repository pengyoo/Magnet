package com.pengyu.magnet.service;

import com.pengyu.magnet.domain.JobApplication;
import com.pengyu.magnet.dto.JobApplicationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobApplicationService {
    JobApplicationDTO apply(Long jobId);

    JobApplicationDTO find(Long id);

    List<JobApplicationDTO> findAll(Pageable pageable, Long userId);
    List<JobApplicationDTO> findAllByCurrentUser(Pageable pageable);

    void modifyState(Long id, JobApplication.Status status);

    long countByCurrentUser();
    long count();

    Page<JobApplicationDTO> findAllByCurrentCompany(Pageable pageable);
}
