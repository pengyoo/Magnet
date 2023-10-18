package com.pengyu.magnet.domain.assessment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "assessment_question")
@DynamicUpdate
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
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(columnDefinition = "text")
    private String standardAnswer;

    public enum Type {
        SINGLE_CHOICE,
        FREE_TEXT,
        MULTIPLE_CHOICE
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", type=" + type +
                ", standardAnswer='" + standardAnswer + '\'' +
                '}';
    }

    @Transient
    private String key = UUID.randomUUID().toString();
}
