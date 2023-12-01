package com.pengyu.magnet.domain.assessment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pengyu.magnet.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "assessment_answer_sheet")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
public class AnswerSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(
            name = "paper_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "answer_sheet_paper_id_fk")
    )
    @JsonIgnore
    private TestPaper testPaper;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "answer_sheet_user_id_fk")
    )
    private User user;

    @OneToMany(mappedBy = "answerSheet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Answer> answerList;

    @Column(name = "score")
    private float score;

}
