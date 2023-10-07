package com.pengyu.magnet.repository.assessment;

import com.pengyu.magnet.domain.assessment.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
