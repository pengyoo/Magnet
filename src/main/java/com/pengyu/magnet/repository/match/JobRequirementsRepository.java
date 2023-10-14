package com.pengyu.magnet.repository.match;

import com.pengyu.magnet.domain.match.JobInsights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRequirementsRepository extends JpaRepository<JobInsights, Long> {
    Optional<JobInsights> findByJobId(Long jobId);
}
