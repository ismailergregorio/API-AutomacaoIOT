package com.example.AutomacaoIOT.DTO.Weather;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WeatherMaxDTO extends WeatherBasicDTO {

    private List<ForecastDayDTO> previsao;
}
