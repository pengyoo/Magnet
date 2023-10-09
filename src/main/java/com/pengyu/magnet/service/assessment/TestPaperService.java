package com.pengyu.magnet.service.assessment;

import com.pengyu.magnet.dto.TestPaperDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TestPaperService {
    TestPaperDTO save(TestPaperDTO testPaperDTO);
    TestPaperDTO find(Long id);
    List<TestPaperDTO> findAll(Pageable pageable, Long userId);

    long count(Long userId);
}
