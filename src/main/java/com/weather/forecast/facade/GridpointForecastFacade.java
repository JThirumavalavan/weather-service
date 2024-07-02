package com.weather.forecast.facade;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import com.weather.forecast.constants.ApiConstants;
import com.weather.forecast.dto.client.response.GridpointForecast;
import com.weather.forecast.exception.WeatherServiceException;

import reactor.core.publisher.Mono;

@Component
public class GridpointForecastFacade {

	private final WebClient webClient;

	@Value("${weather.api.base-url}")
	private String baseUrl;

	@Value("${weather.api.grid-endpoint}")
	private String gridEndpoint;

	public GridpointForecastFacade(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.build();
	}

	/**@Desc - Methods calls downstream endpoint to get weather forecast
	 *
	 * @param locationId
	 * @param gridX
	 * @param gridY
	 * @return
	 */
	public Mono<GridpointForecast> getWeatherForecast(String locationId, String gridX, String gridY) {
		URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl).path(gridEndpoint).buildAndExpand(locationId, gridX, gridY)
				.toUri();
		return webClient.get().uri(uri).retrieve().bodyToMono(GridpointForecast.class)
				.switchIfEmpty(Mono.error(new WeatherServiceException(ApiConstants.DOWN_STREAM_NO_DATA_ERROR_MSG)))
				.onErrorMap(WebClientResponseException.class,
						ex -> new WeatherServiceException(ApiConstants.DOWN_STREAM_ERROR_MSG, ex));

	}

}
