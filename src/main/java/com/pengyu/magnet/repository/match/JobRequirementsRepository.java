package com.pengyu.magnet.repository.match;

import com.pengyu.magnet.domain.Company;
import com.pengyu.magnet.domain.match.JobRequirements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRequirementsRepository extends JpaRepository<JobRequirements, Long> {
    Optional<JobRequirements> findByJobId(Long jobId);
}
