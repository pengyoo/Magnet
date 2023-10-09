package com.pengyu.magnet.repository;

import com.pengyu.magnet.domain.Company;
import com.pengyu.magnet.domain.JobApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    Page<JobApplication> findByUserId(Pageable pageable, Long id);

    long countByUserId(Long userId);
}
