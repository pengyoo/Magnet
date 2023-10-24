package com.pengyu.magnet.repository.assessment;

import com.pengyu.magnet.domain.User;
import com.pengyu.magnet.domain.assessment.AnswerSheet;
import com.pengyu.magnet.domain.assessment.Question;
import com.pengyu.magnet.domain.assessment.TestPaper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerSheetRepository extends JpaRepository<AnswerSheet, Long> {
    List<AnswerSheet> findAllByUser(Pageable pageable, User user);

    long countByUserId(Long userId);

    Optional<AnswerSheet> findByUserAndTestPaper(User user, TestPaper testPaper);
}
