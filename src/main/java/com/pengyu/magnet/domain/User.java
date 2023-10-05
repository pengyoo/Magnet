package com.pengyu.magnet.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * User Entity (User Account)
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Enumerated(EnumType.STRING)
    private Role role;
}
