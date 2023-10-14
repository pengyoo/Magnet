package com.pengyu.magnet.repository;

import com.pengyu.magnet.domain.Company;
import com.pengyu.magnet.domain.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    // Only query job with status ACTIVE and EXPIRED
    @Query("select j from Job j JOIN FETCH j.company where j.company =:company and (j.status=0 or j.status=1)")
    Page<Job> findAllByCompany(Pageable pageable, @Param("company") Company company);

    long countByCompanyId(Long companyId);

    Page<Job> findAllByTitleLike(Pageable pageable, String titleLike);

    long countByTitleLike(String title);
}
