package com.example.AutomacaoIOT.Service.ServicesWeather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.AutomacaoIOT.Model.ModelWeather.OpenWeatherCurrent;
import com.example.AutomacaoIOT.Model.ModelWeather.OpenWeatherForecast;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenWeatherService {
    private final RestClient restClient;

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.unit}")
    private String unit;

    @Value("${weather.lang}")
    private String lang;

    public OpenWeatherCurrent getCurrent(Double lat, Double lon) {

        String url = apiUrl +
                "/weather?lat={lat}&lon={lon}" +
                "&appid={key}" +
                "&units={unit}" +
                "&lang={lang}";

        return restClient.get()
                .uri(url, lat, lon, apiKey, unit, lang)
                .retrieve()
                .body(OpenWeatherCurrent.class);

    }

    public OpenWeatherForecast getForecast(Double lat, Double lon) {

        String url = apiUrl +
                "/forecast?lat={lat}&lon={lon}" +
                "&appid={key}" +
                "&units={unit}" +
                "&lang={lang}";

        return restClient.get()
                .uri(url, lat, lon, apiKey, unit, lang)
                .retrieve()
                .body(OpenWeatherForecast.class);

    }
}
