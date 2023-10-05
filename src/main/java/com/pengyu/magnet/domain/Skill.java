package com.pengyu.magnet.domain;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Skill Entity
 */
@Data
@Entity
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skill;

    // One resume can have multiple Skills
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "resume_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "skill_resume_id_fk")
    )
    private Resume resume;
}
