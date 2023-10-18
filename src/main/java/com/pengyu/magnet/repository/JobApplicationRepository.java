package com.pengyu.magnet.repository;

import com.pengyu.magnet.domain.Company;
import com.pengyu.magnet.domain.JobApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    Page<JobApplication> findByUserId(Pageable pageable, Long id);

    long countByUserId(Long userId);

    JobApplication findByUserIdAndJobId(Long id, Long jobId);

    @Query("select a from JobApplication a JOIN FETCH a.job j JOIN FETCH j.company c where j.company =:company")
    Page<JobApplication> findAllByCompany(Pageable pageable, Company company);
}
