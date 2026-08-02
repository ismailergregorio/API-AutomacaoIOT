package com.example.AutomacaoIOT.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.AutomacaoIOT.DTO.Weather.WeatherBasicDTO;
import com.example.AutomacaoIOT.DTO.Weather.WeatherMaxDTO;
import com.example.AutomacaoIOT.Service.ServicesKeyDevice;
import com.example.AutomacaoIOT.Service.ServicesWeather.WeatherService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/weather")
@CrossOrigin("*")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService service;
    private final ServicesKeyDevice servicesKeyDevice;

    @GetMapping("/basic")
    public WeatherBasicDTO basic(
            @RequestParam Double lat,
            @RequestParam Double lon) {

        return service.basic(lat, lon);
    }

    @GetMapping("/max")
    public WeatherMaxDTO max(
            @RequestParam Double lat,
            @RequestParam Double lon) {

        return service.max(lat, lon);
    }

    @GetMapping("/api-device/basic/{key}")
    public ResponseEntity<WeatherBasicDTO> basicApiDevice(
            @PathVariable String key,
            @RequestParam Double lat,
            @RequestParam Double lon) {

        if (!servicesKeyDevice.validateKeyDevice(key)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(service.basic(lat, lon));
    }

    @GetMapping("/api-device/max/{key}")
    public ResponseEntity<WeatherMaxDTO> maxApiDevice(
            @PathVariable String key,
            @RequestParam Double lat,
            @RequestParam Double lon) {

        if (!servicesKeyDevice.validateKeyDevice(key)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(service.max(lat, lon));
    }
}