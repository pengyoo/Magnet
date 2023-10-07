package com.pengyu.magnet.repository.assessment;

import com.pengyu.magnet.domain.User;
import com.pengyu.magnet.domain.assessment.AnswerSheet;
import com.pengyu.magnet.domain.assessment.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerSheetRepository extends JpaRepository<AnswerSheet, Long> {
    List<AnswerSheet> findAllByUser(Pageable pageable, User user);
}
