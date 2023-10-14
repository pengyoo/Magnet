package com.pengyu.magnet.repository.match;

import com.pengyu.magnet.domain.match.ResumeInsights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeInsightsRepository extends JpaRepository<ResumeInsights, Long> {
    Optional<ResumeInsights> findByResumeId(Long resumeId);
}
