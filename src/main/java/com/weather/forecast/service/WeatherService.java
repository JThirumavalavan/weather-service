package com.weather.forecast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weather.forecast.constants.ApiConstants;
import com.weather.forecast.dto.WeatherResponse;
import com.weather.forecast.exception.WeatherServiceException;
import com.weather.forecast.facade.GridpointForecastFacade;
import com.weather.forecast.mapper.WeatherMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class WeatherService {

	private final GridpointForecastFacade gridpointForecastFacade;
	private final WeatherMapper weatherMapper;

	@Autowired
	public WeatherService(GridpointForecastFacade gridpointForecastFacade, WeatherMapper weatherMapper) {
		this.gridpointForecastFacade = gridpointForecastFacade;
		this.weatherMapper = weatherMapper;
	}

	/**
	 *
	 * @param locationId
	 * @param gridX
	 * @param gridY
	 * @return
	 */
	public Mono<WeatherResponse> getDailyForecast(String locationId, String gridX, String gridY) {
		try {
			return gridpointForecastFacade.getWeatherForecast(locationId, gridX, gridY)
					.map(gridpointForecast -> weatherMapper.toWeatherResponse(gridpointForecast));
		} catch (Exception e) {
			log.error(ApiConstants.EXCEPTION_ERROR_MSG, e);
			throw new WeatherServiceException(ApiConstants.DOWN_STREAM_ERROR_MSG, e);
		}
	}
}
