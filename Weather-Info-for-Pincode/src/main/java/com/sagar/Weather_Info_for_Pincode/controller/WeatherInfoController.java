package com.sagar.Weather_Info_for_Pincode.controller;

import com.sagar.Weather_Info_for_Pincode.model.WeatherInfo;
import com.sagar.Weather_Info_for_Pincode.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class WeatherInfoController {


    @Autowired
    WeatherService weatherService;

    @GetMapping("/weather")
    public ResponseEntity<WeatherInfo> getWeatherInfo(
            @RequestParam Integer pincode,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate for_date) {

        if (for_date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Fetching weather for past dates is not supported due to limitations of the free OpenWeather API plan. Please enter today's date only."
            );
        }

        try {
            WeatherInfo weatherInfo = this.weatherService.getWeatherInfo(pincode, for_date);
            return ResponseEntity.ok(weatherInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
