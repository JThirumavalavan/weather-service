package com.weather.forecast.service;

import com.weather.forecast.dto.WeatherResponse;
import com.weather.forecast.dto.client.response.GridpointForecast;
import com.weather.forecast.exception.WeatherServiceException;
import com.weather.forecast.facade.GridpointForecastFacade;
import com.weather.forecast.mapper.WeatherMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class WeatherServiceTest {

    @Mock
    private GridpointForecastFacade gridpointForecastFacade;

    @Mock
    private WeatherMapper weatherMapper;

    @InjectMocks
    private WeatherService weatherService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetDailyForecast_success() {
        GridpointForecast mockGridpointForecast = new GridpointForecast(); // Create and set up mock forecast data
        WeatherResponse mockWeatherResponse = new WeatherResponse(); // Create and set up mock response data

        when(gridpointForecastFacade.getWeatherForecast(anyString(), anyString(), anyString()))
                .thenReturn(Mono.just(mockGridpointForecast));
        when(weatherMapper.toWeatherResponse(mockGridpointForecast)).thenReturn(mockWeatherResponse);

        Mono<WeatherResponse> weatherResponseMono = weatherService.getDailyForecast("locationId", "gridX", "gridY");

        StepVerifier.create(weatherResponseMono)
                .expectNext(mockWeatherResponse)
                .verifyComplete();
    }

    @Test
    public void testGetDailyForecast_failure() {
        when(gridpointForecastFacade.getWeatherForecast(anyString(), anyString(), anyString()))
                .thenReturn(Mono.error(new WeatherServiceException("Failed to retrieve forecast data")));

        Mono<WeatherResponse> weatherResponseMono = weatherService.getDailyForecast("locationId", "gridX", "gridY");

        StepVerifier.create(weatherResponseMono)
                .expectError(WeatherServiceException.class)
                .verify();
    }
}
