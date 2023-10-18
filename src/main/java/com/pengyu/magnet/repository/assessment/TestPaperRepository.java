package com.pengyu.magnet.repository.assessment;

import com.pengyu.magnet.domain.User;
import com.pengyu.magnet.domain.assessment.TestPaper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestPaperRepository extends JpaRepository<TestPaper, Long> {
    Page<TestPaper> findAllByUser(Pageable pageable, User user);

    long countByUserId(Long userId);

    TestPaper findByJobId(Long jobId);
}
