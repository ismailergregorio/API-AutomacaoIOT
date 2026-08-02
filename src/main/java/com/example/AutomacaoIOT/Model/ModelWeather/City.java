package com.example.AutomacaoIOT.Model.ModelWeather;

import lombok.Data;

@Data
public class City {
    private String name;

    private String country;

    private Coord coord;
}
