package com.example.AutomacaoIOT.Model.ModelWeather;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import java.util.List;

@Data
public class ForecastItem {
    private Main main;

    private List<Weather> weather;

    @JsonProperty("dt_txt")
    private String dataHora;
}
