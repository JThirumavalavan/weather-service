package com.weather.forecast.exception.handler;

import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.weather.forecast.constants.ApiConstants;
import com.weather.forecast.dto.error.ErrorResponse;
import com.weather.forecast.exception.WeatherServiceException;

@RestControllerAdvice
public class WeatherControllerAdvice {

	/**
	 * Handle the bad request errors
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
		String fieldName = ex.getConstraintViolations().stream().map(error -> error.getPropertyPath().toString()).findFirst().get();
		ErrorResponse errorResponse = new ErrorResponse(ApiConstants.BAD_REQUEST_ERROR_CODE,
				ApiConstants.BAD_REQUEST_ERROR_CODE_MSG, ApiConstants.BAD_REQUEST_ERROR_CODE_DESC.concat(fieldName.split(".")[0]));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	/**
	 * Handle the downstream exception.
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(WeatherServiceException.class)
	public ResponseEntity<ErrorResponse> handleWeatherServiceException(Exception ex) {
		ErrorResponse errorResponse = new ErrorResponse(ApiConstants.INTERNAL_SERVER_ERROR_CODE,
				ApiConstants.INTERNAL_SERVER_ERROR_MSG, ApiConstants.INTERNAL_SERVER_ERROR_DESC_DOWNSTREAM);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}

}
