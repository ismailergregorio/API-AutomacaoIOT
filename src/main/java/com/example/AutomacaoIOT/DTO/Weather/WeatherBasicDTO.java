package com.example.AutomacaoIOT.DTO.Weather;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class WeatherBasicDTO {

    private String cidade;

    private Double latitude;

    private Double longitude;

    private Double temperatura;

    private Double temperaturaMinima;

    private Double temperaturaMaxima;

    private Double sensacaoTermica;

    private Integer umidade;

    private Double velocidadeVento;

    private String unidade;

    private String descricao;

    private String icone;

    private LocalDateTime ultimaAtualizacao;
}
