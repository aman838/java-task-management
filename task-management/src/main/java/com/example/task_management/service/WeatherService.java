package com.example.task_management.service;

import com.example.task_management.dto.Weather.LocationCoordinatesDto;
import com.example.task_management.dto.Weather.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WeatherService implements WeatherServiceInterface {
    private final WebClient webClient;

    @Override
    public LocationCoordinatesDto getCoordinatesByCity(String city) {
        if (city.equalsIgnoreCase("Pune")) {
            return new LocationCoordinatesDto(18.5204, 73.8567);
        }

        if (city.equalsIgnoreCase("indore")) {
            return new LocationCoordinatesDto(18.5204, 73.8567);
        }

        if (city.equalsIgnoreCase("Mumbai")) {
            return new LocationCoordinatesDto(19.0760, 72.8777);
        }

        if (city.equalsIgnoreCase("Delhi")) {
            return new LocationCoordinatesDto(28.6139, 77.2090);
        }

        throw new RuntimeException("City not supported: " + city);
    }

    public Mono<WeatherResponse> getWeather(
            double latitude,
            double longitude
    ) {

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("api.open-meteo.com")
                        .path("/v1/forecast")
                        .queryParam("latitude", latitude)
                        .queryParam("longitude", longitude)
                        .queryParam(
                                "current",
                                "temperature_2m,wind_speed_10m"
                        )
                        .build()
                )
                .retrieve()
                .bodyToMono(WeatherResponse.class);
    }

}