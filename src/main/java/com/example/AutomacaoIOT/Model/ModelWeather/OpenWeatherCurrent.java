package com.example.AutomacaoIOT.Model.ModelWeather;

import java.util.List;

import lombok.Data;

@Data
public class OpenWeatherCurrent {
    private Coord coord;

    private List<Weather> weather;

    private Main main;

    private Wind wind;

    private Clouds clouds;

    private Sys sys;

    private String name;

    private Long dt;
}
