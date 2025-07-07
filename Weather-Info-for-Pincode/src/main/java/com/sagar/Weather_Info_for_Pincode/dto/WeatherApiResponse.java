package com.sagar.Weather_Info_for_Pincode.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherApiResponse {

    private List<Weather> weather;
    private String base;
    private Main main;
    private int visibility;
    private Wind wind;
    private long dt;
    private int timezone;
    private int id;
    private String name;
    private int cod;
}
