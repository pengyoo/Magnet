package com.pengyu.magnet.repository;

import com.pengyu.magnet.domain.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeProjectRepository extends JpaRepository<Resume.Project, Long> {
}
