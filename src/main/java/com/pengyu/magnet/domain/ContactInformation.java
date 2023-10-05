package com.pengyu.magnet.domain;

import jakarta.persistence.*;
import lombok.Data;

/**
 * ContactInformation Entity for Resume
 */
@Data
@Entity
public class ContactInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phoneNumber;
    private String email;
    private String address;
    private String city;
    private String country;
    private String linkedInUrl;

    // One contactInformation belongs to one resume
    @OneToOne
    @JoinColumn(name = "resume_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "contact_resume_id_fk")
    )
    private Resume resume;
}
