package com.example.task_management.dto.Weather;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse {
    private  double latitude;
    private double longitude;
    private CurrentWeather current;
}
