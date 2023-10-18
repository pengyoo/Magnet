package com.pengyu.magnet.domain.match;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@Table(name = "match_skill")
@DynamicUpdate
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skill;
    private int weight;

    @ManyToOne
    @JoinColumn(
            name = "job_insights_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "skill_job_insights_id_fk")
    )
    @JsonIgnore
    private JobInsights jobInsights;

    @ManyToOne
    @JoinColumn(
            name = "resume_insights_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "skill_resume_insights_id_fk")
    )
    @JsonIgnore
    private ResumeInsights resumeInsights;

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", skill='" + skill + '\'' +
                ", weight=" + weight +
                '}';
    }
}
