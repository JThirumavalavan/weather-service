package com.weather.forecast.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

	private int errorCode;
	
	private String errorMessage;
	
	private String errorDesciption;
}
