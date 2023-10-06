package com.pengyu.magnet.domain.assessment;

import com.pengyu.magnet.domain.Job;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "assessment_paper")
public class TestPaper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Type type;

    private LocalDateTime createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "job_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "paper_job_id_fk")
    )
    private Job job;

    @OneToMany(mappedBy = "testPaper")
    private List<Question> questionList;

    public enum Type {
        TECHNOLOGY,
        BEHAVIOUR
    }
}
