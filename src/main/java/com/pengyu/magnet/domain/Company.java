package com.pengyu.magnet.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

/**
 * Company Entity
 */
@Data
@Entity
@Table(name="company")
@DynamicUpdate
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;
    private String city;
    private String address;
    private int scale;
    private String industry;

    private LocalDateTime createdAt;

    @Column(columnDefinition = "text")
    private String description;

    // One company belongs to one user account
    @OneToOne
    @JoinColumn(name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "company_user_id_fk")
    )
    private User user;

    // One company has multiple jobs
//    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<Job> jobList;
}
