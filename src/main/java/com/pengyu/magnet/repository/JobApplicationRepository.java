package com.pengyu.magnet.repository;

import com.pengyu.magnet.domain.Company;
import com.pengyu.magnet.domain.JobApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    Page<JobApplication> findByUserId(Pageable pageable, Long id);

    long countByUserId(Long userId);

    JobApplication findByUserIdAndJobId(Long id, Long jobId);

    @Query("select a from JobApplication a JOIN FETCH a.job j JOIN FETCH j.company c where j.company =:company")
    Page<JobApplication> findAllByCompany(Pageable pageable, Company company);

    @Query(value = "SELECT COALESCE(COUNT(u.id), 0) AS registrationCount " +
            "FROM (SELECT 1 AS month UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 " +
            "      UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 " +
            "      UNION SELECT 11 UNION SELECT 12) AS m " +
            "LEFT JOIN job_application u ON MONTH(u.applied_date) = m.month " +
            "GROUP BY m.month " +
            "ORDER BY m.month", nativeQuery = true)
    List<Long> getJobApplicationCounts();
}
