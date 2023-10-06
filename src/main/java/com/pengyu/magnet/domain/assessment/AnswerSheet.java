package com.pengyu.magnet.domain.assessment;

import com.pengyu.magnet.domain.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "assessment_answer_sheet")
public class AnswerSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "paper_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "answer_sheet_paper_id_fk")
    )
    private TestPaper testPaper;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "answer_sheet_user_id_fk")
    )
    private User user;

    @OneToMany(mappedBy = "answerSheet")
    private List<Answer> answerList;

}
