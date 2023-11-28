package com.pengyu.magnet.repository;

import com.pengyu.magnet.domain.Company;
import com.pengyu.magnet.domain.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    // Only query job with status ACTIVE and EXPIRED
    @Query("select j from Job j JOIN FETCH j.company where j.company =:company and (j.status='ACTIVE' or j.status='PAUSED')")
    Page<Job> findAllByCompany(Pageable pageable, @Param("company") Company company);

    long countByCompanyId(Long companyId);

    Page<Job> findAllByTitleLike(Pageable pageable, String titleLike);

    long countByTitleLike(String title);

    @Query(value = "SELECT COALESCE(COUNT(u.id), 0) AS registrationCount " +
            "FROM (SELECT 1 AS month UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 " +
            "      UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 " +
            "      UNION SELECT 11 UNION SELECT 12) AS m " +
            "LEFT JOIN job u ON MONTH(u.created_at) = m.month " +
            "GROUP BY m.month " +
            "ORDER BY m.month", nativeQuery = true)
    List<Long> getJobCounts();
}
