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
    private String description;
    private String country;
    private String city;
    private String address;

}
