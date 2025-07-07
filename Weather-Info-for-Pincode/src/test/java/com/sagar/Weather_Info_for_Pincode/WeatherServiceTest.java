package com.sagar.Weather_Info_for_Pincode;

import com.sagar.Weather_Info_for_Pincode.dto.Main;
import com.sagar.Weather_Info_for_Pincode.dto.Weather;
import com.sagar.Weather_Info_for_Pincode.dto.WeatherApiResponse;
import com.sagar.Weather_Info_for_Pincode.dto.Wind;
import com.sagar.Weather_Info_for_Pincode.model.Pincode;
import com.sagar.Weather_Info_for_Pincode.model.WeatherInfo;
import com.sagar.Weather_Info_for_Pincode.repository.PincodeRepository;
import com.sagar.Weather_Info_for_Pincode.repository.WeatherInfoRepository;
import com.sagar.Weather_Info_for_Pincode.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class WeatherServiceTest {

    @InjectMocks
    private WeatherService weatherService;

    @Mock
    private WeatherInfoRepository weatherInfoRepository;

    @Mock
    private PincodeRepository pincodeRepository;

    @Mock
    private RestTemplate restTemplate;

    private static final Integer PINCODE = 12345;
    private static final LocalDate DATE = LocalDate.of(2023, 3, 2);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(weatherService, "OPEN_WEATHER_API_KEY", "dummyapikey");
    }

    @Test
    void testWeatherInfoFromDatabase() throws Exception {
        WeatherInfo mockInfo = new WeatherInfo();
        mockInfo.setPincode(PINCODE);
        mockInfo.setDate(DATE);
        mockInfo.setTemperature(25.5);

        when(weatherInfoRepository.findByPincodeAndDate(PINCODE, DATE))
                .thenReturn(Optional.of(mockInfo));

        WeatherInfo result = weatherService.getWeatherInfo(PINCODE, DATE);

        assertEquals(25.5, result.getTemperature());
        verify(weatherInfoRepository, times(1)).findByPincodeAndDate(PINCODE, DATE);
        verifyNoMoreInteractions(pincodeRepository);
    }

    @Test
    void testPincodeInDbButWeatherNotCached() throws Exception {
        when(weatherInfoRepository.findByPincodeAndDate(PINCODE, DATE))
                .thenReturn(Optional.empty());

        Pincode pinLoc = new Pincode(PINCODE, 18.5204, 73.8567);
        when(pincodeRepository.findById(PINCODE)).thenReturn(Optional.of(pinLoc));

        WeatherApiResponse mockResponse = new WeatherApiResponse();
        Main main = new Main();
        main.setTemp(30.0);
        main.setHumidity(60);
        main.setPressure(1010);
        mockResponse.setMain(main);
        mockResponse.setName("TestCity");

        Wind wind = new Wind();
        wind.setSpeed(4.2);
        mockResponse.setWind(wind);

        Weather weather = new Weather();
        weather.setDescription("clear sky");
        mockResponse.setWeather(java.util.List.of(weather));

        when(restTemplate.getForEntity(anyString(), eq(WeatherApiResponse.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        WeatherInfo result = weatherService.getWeatherInfo(PINCODE, DATE);

        assertEquals(30.0, result.getTemperature());
        assertEquals(60, result.getHumidity());
        assertEquals(1010, result.getPressure());
        assertEquals("clear sky", result.getDescription());
        assertEquals("TestCity", result.getPlace());
        assertEquals(4.2, result.getWindSpeed());

        verify(weatherInfoRepository).save(any(WeatherInfo.class));
    }
}
