package com.pengyu.magnet.service;

import com.pengyu.magnet.domain.assessment.TestPaper;
import com.pengyu.magnet.dto.TestPaperDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestPaperService {
    TestPaperDTO save(TestPaperDTO testPaperDTO);
    TestPaperDTO find(Long id);
    List<TestPaperDTO> findAllByCurrentUser(Pageable pageable);
}
