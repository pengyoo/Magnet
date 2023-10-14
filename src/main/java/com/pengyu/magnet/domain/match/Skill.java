package com.pengyu.magnet.domain.match;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "match_skill")
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
}
