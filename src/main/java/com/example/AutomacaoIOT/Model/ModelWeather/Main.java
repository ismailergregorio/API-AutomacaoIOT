package com.example.AutomacaoIOT.Model.ModelWeather;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Main {
    private Double temp;

    @JsonProperty("feels_like")
    private Double feelsLike;

    @JsonProperty("temp_min")
    private Double tempMin;

    @JsonProperty("temp_max")
    private Double tempMax;

    private Integer pressure;

    private Integer humidity;
}
