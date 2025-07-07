package com.sagar.Weather_Info_for_Pincode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WeatherInfoForPincodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherInfoForPincodeApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
