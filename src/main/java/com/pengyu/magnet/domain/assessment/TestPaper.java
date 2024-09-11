package com.pengyu.magnet.domain.assessment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pengyu.magnet.domain.Job;
import com.pengyu.magnet.domain.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "assessment_paper")
@DynamicUpdate
public class TestPaper {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(
            name = "job_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "paper_job_id_fk")
    )
    private Job job;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "paper_user_id_fk")
    )
    private User user;

    @OneToMany(mappedBy = "testPaper", cascade = CascadeType.ALL)
    private List<Question> questionList;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Type {
        TECHNOLOGY,
        BEHAVIOUR
    }

    public enum Status {
        ACTIVE,
        INACTIVE
    }

    @Override
    public String toString() {
        return "TestPaper{" +
                "id=" + id +
                ", type=" + type +
                ", createdAt=" + createdAt +
                ", job=" + job +
                ", user=" + user +
                ", questionList=" + questionList +
                ", status=" + status +
                '}';
    }
}
