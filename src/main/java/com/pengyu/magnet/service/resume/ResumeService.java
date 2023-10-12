package com.pengyu.magnet.service.resume;

import com.pengyu.magnet.dto.ResumeDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResumeService {
    ResumeDTO save(ResumeDTO resumeRequest);
    ResumeDTO find(Long id);
    List<ResumeDTO> findAll(Pageable pageable);

    long count();

    ResumeDTO findMyResume();
}
