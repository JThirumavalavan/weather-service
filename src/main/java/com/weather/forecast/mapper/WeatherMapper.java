package com.weather.forecast.mapper;

import com.weather.forecast.dto.DailyForecast;
import com.weather.forecast.dto.WeatherResponse;
import com.weather.forecast.dto.client.response.GridpointForecast;
import com.weather.forecast.dto.client.response.Period;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WeatherMapper {

	private final ModelMapper modelMapper;

	public WeatherMapper() {
		this.modelMapper = new ModelMapper();

		Converter<Integer, Double> fahrenheitToCelsiusConverter = new Converter<Integer, Double>() {
			@Override
			public Double convert(MappingContext<Integer, Double> context) {
				Integer fahrenheit = context.getSource();
				if (fahrenheit == null) {
					return null;
				}
				return Double.valueOf(String.format("%.2f", (fahrenheit - 32) * 5.0 / 9.0));

			}
		};
		this.modelMapper.typeMap(Period.class, DailyForecast.class)
				.addMapping(Period::getName, DailyForecast::setDayName)
				.addMapping(Period::getShortForecast, DailyForecast::setForecastBlurp)
				.addMappings(mapper -> mapper.using(fahrenheitToCelsiusConverter).map(Period::getTemperature,
						DailyForecast::setTempHighCelsius));

	}

	/**
	 * @Desc - Convert the response from downstream response to API response
	 * @param gridpointForecast
	 * @return
	 */
	public WeatherResponse toWeatherResponse(GridpointForecast gridpointForecast) {
		List<DailyForecast> dailyForecasts = gridpointForecast.getProperties().getPeriods().stream()
				.map(period -> modelMapper.map(period, DailyForecast.class)).collect(Collectors.toList());
		var response = WeatherResponse.builder().daily(dailyForecasts).build();

		return response;
	}
}