# Weather API

## Overview

This project is a Kotlin Spring Boot reactive application that provides weather forecasts.
# Prerequisties
  - Java : 1.11
  - Spring Boot : 2.7.18

## Features

- Fetch weather forecast from external API
- Request validation
- Error handling with RestControllerAdvice
- Parameterization via application.yml
- Integration tests
- Swagger documentation


## Getting Started

1. Clone the repository.
2. Build the project using `./mvn build`.
3. Run the application using `./mvn springboot:run`.


## API Endpoints

### POST /api/v1/weather/forecast

Fetch the weather forecast for a given location.

#### Swagger
http://localhost:8082/webjars/swagger-ui/index.html
