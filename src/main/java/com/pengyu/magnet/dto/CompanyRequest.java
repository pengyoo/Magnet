package com.pengyu.magnet.dto;

import com.pengyu.magnet.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Company Request DTO
 */
@Data
public class CompanyRequest {

    private Long id;

    @NotBlank(message = "Company name can not be blank.")
    private String name;

    @NotBlank(message = "Country can not be blank.")
    private String country;

    @NotBlank(message = "City can not be blank.")
    private String city;

    @NotBlank(message = "Address can not be blank")
    private String address;
    private int scale;

    @NotBlank(message = "Industry can not be blank.")
    private String industry;

    @NotBlank(message = "Description can not be blank.")
    private String description;

}
