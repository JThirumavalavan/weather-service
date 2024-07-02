package com.weather.forecast.controller;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weather.forecast.dto.WeatherResponse;
import com.weather.forecast.dto.error.ErrorResponse;
import com.weather.forecast.service.WeatherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/weather")
@Validated
@Tag(name = "Weather Service API", description = "Weather API to get the forecast for the grid location")
public class WeatherServiceController {

	@Autowired
	private WeatherService weatherService;

	/**
	 * @Description - This method retrieve the forecast for the give locationId and
	 *              co-ordinates
	 * @param locationId
	 * @param gridX
	 * @param gridY
	 * @return
	 */
	@GetMapping("/forecast/{locationId}/{gridX}/{gridY}")
	@Operation(description = "Get daily grid forecast", parameters = {
			@Parameter(name = "locationId", in = ParameterIn.PATH, required = true, description = "Office location"),
			@Parameter(name = "gridX", in = ParameterIn.PATH, required = true, description = "Grid latitude co-ordinates"),
			@Parameter(name = "gridY", in = ParameterIn.PATH, required = true, description = "Grid longitude co-ordinates") })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Get the weather forecast", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = WeatherResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }),
			@ApiResponse(responseCode = "500", description = "Server Error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) }) })
	public Mono<WeatherResponse> getForecast(
			@PathVariable("locationId") @Pattern(regexp = "^[a-zA-Z]+$", message = "LocationId cannot be blank") String locationId,
			@PathVariable("gridX") @Positive(message = "GridX cannot be blank") String gridX,
			@PathVariable("gridY") @Positive(message = "GridY cannot be blank") String gridY) {
		return weatherService.getDailyForecast(locationId, gridX, gridY);
	}
}
