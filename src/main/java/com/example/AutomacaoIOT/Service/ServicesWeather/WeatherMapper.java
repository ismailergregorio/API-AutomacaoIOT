package com.example.AutomacaoIOT.Service.ServicesWeather;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.stereotype.Component;

import com.example.AutomacaoIOT.DTO.Weather.WeatherBasicDTO;
import com.example.AutomacaoIOT.Model.ModelWeather.OpenWeatherCurrent;
@Component
public class WeatherMapper {
    public WeatherBasicDTO basic(OpenWeatherCurrent current) {

        WeatherBasicDTO dto = new WeatherBasicDTO();

        dto.setCidade(current.getName());

        dto.setLatitude(current.getCoord().getLat());

        dto.setLongitude(current.getCoord().getLon());

        dto.setTemperatura(current.getMain().getTemp());

        dto.setTemperaturaMinima(current.getMain().getTempMin());

        dto.setTemperaturaMaxima(current.getMain().getTempMax());

        dto.setSensacaoTermica(current.getMain().getFeelsLike());

        dto.setUmidade(current.getMain().getHumidity());

        dto.setVelocidadeVento(current.getWind().getSpeed() * 3.6);

        dto.setDescricao(current.getWeather().get(0).getDescription());

        dto.setIcone(current.getWeather().get(0).getIcon());

        dto.setUnidade("°C");

        dto.setUltimaAtualizacao(

                LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(current.getDt()),
                        ZoneId.systemDefault())

        );

        return dto;

    }
}
