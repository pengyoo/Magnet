package com.pengyu.magnet.domain.assessment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "assessment_answer")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question", columnDefinition = "text")
    private String questionText;

    @Column(columnDefinition = "text")
    private String answer;


    @ManyToOne
    @JoinColumn(
            name = "answer_sheet_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "answer_answer_sheet_id_fk")
    )
    @JsonIgnore
    private AnswerSheet answerSheet;

    @ManyToOne
    @JoinColumn(
            name = "question_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "answer_question_id_fk")
    )
    @JsonIgnore
    private Question question;

}
