package com.example.AutomacaoIOT.Model.ModelWeather;

import java.util.List;

import lombok.Data;
@Data
public class OpenWeatherForecast {
    private List<ForecastItem> list;

    private City city;
}
