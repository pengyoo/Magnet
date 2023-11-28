package com.pengyu.magnet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinearStatisticsDataResponse {
    private String label;
    private List<Long> data;
    private double tension = 0.4;
    private String borderColor = "#0891b2";
    private String backgroundColor = "#0891b2";

}
