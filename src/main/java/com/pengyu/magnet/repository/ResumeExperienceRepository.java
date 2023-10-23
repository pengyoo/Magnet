package com.pengyu.magnet.repository;

import com.pengyu.magnet.domain.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeExperienceRepository extends JpaRepository<Resume.Experience, Long> {
}
