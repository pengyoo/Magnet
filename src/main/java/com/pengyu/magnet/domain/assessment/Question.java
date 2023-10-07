package com.pengyu.magnet.domain.assessment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "assessment_question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String question;

    @ManyToOne
    @JoinColumn(
            name = "paper_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "question_paper_id_fk")
    )
    @JsonIgnore
    private TestPaper testPaper;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<OptionAnswer> optionList;

    private Type type;

    @Column(columnDefinition = "text")
    private String standardAnswer;

    public enum Type {
        SINGLE_CHOICE,
        FREE_TEXT,
        MULTIPLE_CHOICE
    }
}
