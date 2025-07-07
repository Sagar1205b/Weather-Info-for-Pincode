package com.sagar.Weather_Info_for_Pincode.service;


import com.sagar.Weather_Info_for_Pincode.dto.GeocodingApiResponse;
import com.sagar.Weather_Info_for_Pincode.dto.WeatherApiResponse;
import com.sagar.Weather_Info_for_Pincode.model.Pincode;
import com.sagar.Weather_Info_for_Pincode.model.WeatherInfo;
import com.sagar.Weather_Info_for_Pincode.repository.PincodeRepository;
import com.sagar.Weather_Info_for_Pincode.repository.WeatherInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherInfoRepository weatherInfoRepository;

    @Autowired
    private PincodeRepository pincodeLocationRepository;

    @Value("${openweather.api.key}")
    private String OPEN_WEATHER_API_KEY;

    public Pincode getPincodeLocation(Integer pincode) throws Exception {

        String url = "https://api.openweathermap.org/geo/1.0/zip?zip=" + pincode + ",in&appid=" + OPEN_WEATHER_API_KEY;
//        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GeocodingApiResponse> response = restTemplate.getForEntity(url, GeocodingApiResponse.class);
        double latitude = 0, longitude = 0;
        if (response.getStatusCode().is2xxSuccessful()) {
            latitude = response.getBody().getLat();
            longitude = response.getBody().getLon();
        } else
            throw new Exception(response.getStatusCode().toString());

        return new Pincode(pincode, latitude, longitude);
    }

    public WeatherApiResponse getWeatherApiResponse(double latitude, double longitude, LocalDate date)
            throws Exception {

        long unixTimestamp = date.atStartOfDay(ZoneOffset.UTC).toEpochSecond();
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid="
                + OPEN_WEATHER_API_KEY + "&dt=" + unixTimestamp;
//        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<WeatherApiResponse> response = restTemplate.getForEntity(url, WeatherApiResponse.class);
        if (response.getStatusCode().isError()) {
            throw new Exception(response.getStatusCode().toString());
        }
        return response.getBody();
    }


    public WeatherInfo getWeatherInfo(Integer pincode, LocalDate date) throws Exception {

        Optional<WeatherInfo> optionalWeatherInfo=this.weatherInfoRepository.findByPincodeAndDate(pincode, date);

        if(optionalWeatherInfo.isPresent()) return optionalWeatherInfo.get();

        double latitude;
        double longitude;

        Optional<Pincode> optionalPincodeLocation = null;
        try {
            optionalPincodeLocation = this.pincodeLocationRepository.findById(pincode);
        } catch (DataAccessException ex) {
            ex.printStackTrace();
        }

        if (optionalPincodeLocation.isPresent()) {
            latitude = optionalPincodeLocation.get().getLatitude();
            longitude = optionalPincodeLocation.get().getLongitude();
        }

        else {
            Pincode pincodeLocation = getPincodeLocation(pincode);
            latitude = pincodeLocation.getLatitude();
            longitude = pincodeLocation.getLongitude();
            this.pincodeLocationRepository.save(pincodeLocation);
        }

        WeatherApiResponse weatherApiResponse = getWeatherApiResponse(latitude, longitude, date);

        // convert apiresponse to requred info;
        WeatherInfo weatherInfo = new WeatherInfo();
        if (weatherApiResponse != null) {
            weatherInfo.setPincode(pincode);
            weatherInfo.setDate(date);
            weatherInfo.setTemperature(weatherApiResponse.getMain().getTemp());
            weatherInfo.setHumidity(weatherApiResponse.getMain().getHumidity());
            weatherInfo.setPressure(weatherApiResponse.getMain().getPressure());
            weatherInfo.setWindSpeed(weatherApiResponse.getWind().getSpeed());
            weatherInfo.setDescription(weatherApiResponse.getWeather().get(0).getDescription());
            weatherInfo.setPlace(weatherApiResponse.getName());
            this.weatherInfoRepository.save(weatherInfo);
        }
        System.out.println(weatherInfo.toString());
        return weatherInfo;
    }

}

