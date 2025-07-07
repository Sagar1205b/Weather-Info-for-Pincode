package com.sagar.Weather_Info_for_Pincode.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Weather {

    private int id;
    private String main;
    private String description;
    private String icon;
}
