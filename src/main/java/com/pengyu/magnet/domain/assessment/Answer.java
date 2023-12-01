package com.pengyu.magnet.domain.assessment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "assessment_answer")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question", columnDefinition = "text")
    private String questionText;

    @Column(name = "example_answer", columnDefinition = "text")
    private String exampleAnswer;

    @Column(columnDefinition = "text")
    private String answer;

    @JsonIgnore
    @Column(name="score")
    private float score;


    @ManyToOne
    @JoinColumn(
            name = "answer_sheet_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "answer_answer_sheet_id_fk")
    )
    @JsonIgnore
    private AnswerSheet answerSheet;

}
