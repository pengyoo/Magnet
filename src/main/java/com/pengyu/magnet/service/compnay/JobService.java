package com.pengyu.magnet.service.compnay;

import com.pengyu.magnet.domain.Job;
import com.pengyu.magnet.dto.JobRequest;
import com.pengyu.magnet.dto.JobResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {
    JobResponse save(JobRequest jobRequest);
    JobResponse find(Long id);
    List<JobResponse> findAll(Pageable pageable);

    List<JobResponse> findAll(Pageable pageable, Long companyId);

    long count(Long companyId);
}
