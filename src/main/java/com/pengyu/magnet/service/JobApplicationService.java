package com.pengyu.magnet.service;

import com.pengyu.magnet.domain.JobApplication;
import com.pengyu.magnet.dto.JobApplicationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobApplicationService {
    JobApplicationResponse apply(Long jobId);

    JobApplicationResponse find(Long id);

    List<JobApplicationResponse> findAll(Pageable pageable, Long userId);
    List<JobApplicationResponse> findAllByCurrentUser(Pageable pageable);

    void modifyState(Long id, JobApplication.Status status);

    long countByCurrentUser();
    long count();

    Page<JobApplicationResponse> findAllByCurrentCompany(Pageable pageable);
}
