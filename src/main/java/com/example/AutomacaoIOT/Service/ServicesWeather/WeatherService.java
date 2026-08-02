package com.example.AutomacaoIOT.Service.ServicesWeather;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.AutomacaoIOT.DTO.Weather.ForecastDayDTO;
import com.example.AutomacaoIOT.DTO.Weather.WeatherBasicDTO;
import com.example.AutomacaoIOT.DTO.Weather.WeatherMaxDTO;
import com.example.AutomacaoIOT.Model.ModelWeather.ForecastItem;
import com.example.AutomacaoIOT.Model.ModelWeather.OpenWeatherForecast;

@Service
public class WeatherService {

    private final OpenWeatherService openWeather;

    private final WeatherMapper mapper;

    public WeatherService(OpenWeatherService openWeather,
            WeatherMapper mapper) {

        this.openWeather = openWeather;
        this.mapper = mapper;

    }

    public WeatherBasicDTO basic(Double lat, Double lon) {

        return mapper.basic(
                openWeather.getCurrent(lat, lon));

    }

    public WeatherMaxDTO max(Double lat, Double lon) {

        WeatherMaxDTO dto = new WeatherMaxDTO();

        WeatherBasicDTO basic = basic(lat, lon);

        dto.setCidade(basic.getCidade());
        dto.setLatitude(basic.getLatitude());
        dto.setLongitude(basic.getLongitude());
        dto.setTemperatura(basic.getTemperatura());
        dto.setTemperaturaMinima(basic.getTemperaturaMinima());
        dto.setTemperaturaMaxima(basic.getTemperaturaMaxima());
        dto.setSensacaoTermica(basic.getSensacaoTermica());
        dto.setUmidade(basic.getUmidade());
        dto.setVelocidadeVento(basic.getVelocidadeVento());
        dto.setDescricao(basic.getDescricao());
        dto.setIcone(basic.getIcone());
        dto.setUnidade(basic.getUnidade());
        dto.setUltimaAtualizacao(basic.getUltimaAtualizacao());

        OpenWeatherForecast forecast = openWeather.getForecast(lat, lon);

        dto.setPrevisao(
                agruparDias(forecast));

        return dto;

    }

    private List<ForecastDayDTO> agruparDias(OpenWeatherForecast forecast) {

        Map<LocalDate, ForecastDayDTO> dias = new LinkedHashMap<>();

        for (ForecastItem item : forecast.getList()) {

            LocalDate data = LocalDate.parse(
                    item.getDataHora().substring(0, 10));

            ForecastDayDTO dia = dias.get(data);

            if (dia == null) {

                dia = new ForecastDayDTO();

                dia.setData(data);

                dia.setTemperaturaMinima(
                        item.getMain().getTempMin());

                dia.setTemperaturaMaxima(
                        item.getMain().getTempMax());

                dia.setDescricao(
                        item.getWeather().get(0).getDescription());

                dia.setIcone(
                        item.getWeather().get(0).getIcon());

                dias.put(data, dia);

            } else {

                dia.setTemperaturaMinima(

                        Math.min(
                                dia.getTemperaturaMinima(),
                                item.getMain().getTempMin()

                        )

                );

                dia.setTemperaturaMaxima(

                        Math.max(

                                dia.getTemperaturaMaxima(),
                                item.getMain().getTempMax()

                        )

                );

            }

        }

        return new ArrayList<>(
                dias.values());

    }

}
