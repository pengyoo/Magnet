package com.pengyu.magnet.dto;

import com.pengyu.magnet.domain.User;
import lombok.Data;

/**
 * Company Request DTO
 */
@Data
public class CompanyRequest {

    private Long id;
    private String name;
    private String country;
    private String city;
    private String address;
    private int scale;
    private String industry;
    private String description;

}
