package com.pengyu.magnet.domain;

import jakarta.persistence.*;
import lombok.Data;

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
    @Column(columnDefinition = "text")
    private String description;
    private String country;
    private String city;
    private String address;

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
