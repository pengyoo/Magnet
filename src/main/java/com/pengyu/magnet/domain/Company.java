package com.pengyu.magnet.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Company Entity
 */
@Data
@Entity
@Table(name="company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String address;

    // One company belongs to one user account
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "company_user_id_fk")
    )
    private User user;

    // One company has multiple jobs
    @OneToMany(mappedBy = "company")
    private List<Job> jobList;
}
