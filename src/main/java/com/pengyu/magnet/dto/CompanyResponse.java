package com.pengyu.magnet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pengyu.magnet.domain.Job;
import com.pengyu.magnet.domain.User;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

/**
 * Company Response DTO
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyResponse {

    private Long id;
    private String name;
    private String country;
    private String city;
    private String address;
    private int scale;
    private String industry;
    private String description;

    @JsonProperty("user")
    private UserResponse userData;

}
