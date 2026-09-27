package com.example.task_management.service;

import com.example.task_management.dto.Weather.LocationCoordinatesDto;
import com.example.task_management.dto.Weather.WeatherResponse;
import reactor.core.publisher.Mono;

public interface WeatherServiceInterface {
    public LocationCoordinatesDto getCoordinatesByCity(String city);
    public Mono<WeatherResponse> getWeather(double latitude, double longitude);
}
