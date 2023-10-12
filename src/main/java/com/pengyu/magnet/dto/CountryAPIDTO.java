package com.pengyu.magnet.dto;


import lombok.Data;

@Data
public class CountryAPIDTO {
    private Name name;

    @Data
    public static class Name {
        private String common;
        private String official;
    }
}
