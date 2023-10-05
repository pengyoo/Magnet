package com.pengyu.magnet.repository;

import com.pengyu.magnet.domain.Job;
import com.pengyu.magnet.dto.JobResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    Page<Job> findAllByCompanyId(Pageable pageable, Long companyId);
}
