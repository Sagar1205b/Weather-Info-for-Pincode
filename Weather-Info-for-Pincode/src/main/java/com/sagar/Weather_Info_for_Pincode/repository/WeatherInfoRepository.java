package com.sagar.Weather_Info_for_Pincode.repository;

import com.sagar.Weather_Info_for_Pincode.model.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WeatherInfoRepository extends JpaRepository<WeatherInfo,Integer> {
    Optional<WeatherInfo> findByPincodeAndDate(Integer pincode, LocalDate date);
}
