package com.weather.forecast.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.weather.forecast.dto.WeatherResponse;
import com.weather.forecast.service.WeatherService;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class WeatherServiceControllerTest {

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherServiceController weatherServiceController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testForecastEndpoint() {
        String locationId = "locationId";
        String gridX = "gridX";
        String gridY = "gridY";

        WeatherResponse mockWeatherResponse = new WeatherResponse(); // Create mock response data

        when(weatherService.getDailyForecast(locationId, gridX, gridY))
                .thenReturn(Mono.just(mockWeatherResponse));

        Mono<WeatherResponse> responseMono = weatherServiceController.getForecast(locationId, gridX, gridY);

        StepVerifier.create(responseMono)
                .expectNext(mockWeatherResponse)
                .verifyComplete();
    }
}
