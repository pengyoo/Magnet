package com.pengyu.magnet.repository.assessment;

import com.pengyu.magnet.domain.assessment.Question;
import com.pengyu.magnet.domain.assessment.TestPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
