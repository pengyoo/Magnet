package com.pengyu.magnet.dto;

import com.pengyu.magnet.domain.Job;
import com.pengyu.magnet.domain.User;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

/**
 * Company Response DTO
 */
@Data
public class CompanyResponse {

    private Long id;
    private String name;
    private String description;
    private String address;

    private UserResponse userData;

}
