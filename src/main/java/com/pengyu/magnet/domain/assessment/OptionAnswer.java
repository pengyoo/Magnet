package com.pengyu.magnet.domain.assessment;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "assessment_option_answer")
public class OptionAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String optionAnswer;

    @ManyToOne
    @JoinColumn(
            name = "question_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "option_answer_question_id_fk")
    )
    @JsonIgnore
    private Question question;
}
