package com.pengyu.magnet.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * User Entity (User Account)
 */
@Entity
@Data
public class User {

    // There will be 3 types of Role in this system: JOB_SEEKER, COMPANY, ADMIN
    public enum Role {
        JOB_SEEKER,
        COMPANY,
        ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private Role role;
}