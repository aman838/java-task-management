package com.example.task_management.dto.Weather;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentWeather {
    private double temperature_2m;
    private double wind_speed_10m;
}
