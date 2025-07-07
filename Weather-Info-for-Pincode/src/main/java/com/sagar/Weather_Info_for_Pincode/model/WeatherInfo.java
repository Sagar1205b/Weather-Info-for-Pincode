package com.sagar.Weather_Info_for_Pincode.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "weather_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer pincode;

    private String place;

    private LocalDate date;

    private double temperature;

    private int humidity;

    private int pressure;

    private double windSpeed;

    private String description;

}
