package com.pengyu.magnet.repository;

import com.pengyu.magnet.domain.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeSkillRepository extends JpaRepository<Resume.Skill, Long> {
}
