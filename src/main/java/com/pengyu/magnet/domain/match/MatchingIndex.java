package com.pengyu.magnet.domain.match;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pengyu.magnet.domain.Job;
import com.pengyu.magnet.domain.Resume;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Matching Index between Job and Resume
 */
@Data
@Entity
@Table(name = "match_matching_index")
public class MatchingIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "resume_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "match_index_resume_id_fk")
    )
    @JsonIgnore
    private Resume resume;

    @ManyToOne
    @JoinColumn(name = "job_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "match_index_job_id_fk")
    )
    @JsonIgnore
    private Job job;
    private float degree;
    private float major;
    private float experience;
    private float skill;
    private float language;
    private float overall;
}
