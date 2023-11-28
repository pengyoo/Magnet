package com.pengyu.magnet.service.resume;

import com.pengyu.magnet.dto.ResumeDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResumeService {
    ResumeDTO save(ResumeDTO resumeRequest);
    ResumeDTO find(Long id);
    List<ResumeDTO> findAll(Pageable pageable);

    long count();

    ResumeDTO findMyResumeDTO();

    ResumeDTO findResumeByUserId(Long id);

    void deleteSkill(Long id);

    void deleteEducation(Long id);

    void deleteExperience(Long id);

    void deleteProject(Long id);

    List<Long> getResumeCounts();
}
