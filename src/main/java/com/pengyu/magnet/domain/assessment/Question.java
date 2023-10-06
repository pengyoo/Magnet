package com.pengyu.magnet.domain.assessment;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "assessment_question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String question;

    private LocalDateTime createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "paper_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "question_paper_id_fk")
    )
    private TestPaper testPaper;

    private Type type;

    private String standardAnswer;

    public enum Type {
        SINGLE_CHOICE,
        QUESTION_ANSWER,
        MULTIPLE_CHOICE
    }
}
