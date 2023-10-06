package com.pengyu.magnet.domain.assessment;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "assessment_answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question", columnDefinition = "text")
    private String questionText;

    @Column(columnDefinition = "text")
    private String answer;

    private LocalDateTime createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "answer_sheet_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "answer_answer_sheet_id_fk")
    )
    private AnswerSheet answerSheet;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "question_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "answer_question_id_fk")
    )
    private Question question;

}
