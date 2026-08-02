package com.example.AutomacaoIOT.DTO.Weather;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ForecastDayDTO {

    private LocalDate data;

    private Double temperaturaMinima;

    private Double temperaturaMaxima;

    private String descricao;

    private String icone;
}
