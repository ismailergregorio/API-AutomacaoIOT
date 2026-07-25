package com.example.AutomacaoIOT.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AutomacaoIOT.DTO.Device.DtoCreateDevice;
import com.example.AutomacaoIOT.DTO.Device.DtoUpdateDevice;
import com.example.AutomacaoIOT.Service.ServicesDevice;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("device")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ControllersDevice {

    private final ServicesDevice servicesDevice;

    @PostMapping()
    private ResponseEntity<?> CriarDispositivo(Authentication authentication, @RequestBody DtoCreateDevice device) {
        String email = authentication.getName();
        System.out.println("Email do usuário autenticado: " + email);
        return servicesDevice.CreateDevice(device, email);
    }

    @GetMapping("/devices")
    public ResponseEntity<?> getDispositivos(Authentication authentication) {
        String email = authentication.getName();
        return servicesDevice.Devices(email);
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<?> getDispositivo(Authentication authentication, @PathVariable String deviceId) {
        String email = authentication.getName();
        return servicesDevice.Device(deviceId, email);
    }

    @PutMapping()
    public ResponseEntity<?> updadeDispositivo(Authentication authentication, @RequestBody DtoUpdateDevice devices) {
        String email = authentication.getName();
        return servicesDevice.UpdateDevice(devices,email);
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<?> deleteDispositivo(Authentication authentication, @PathVariable String deviceId) {
        String email = authentication.getName();
        return servicesDevice.DeleteDevice(deviceId,email);
    }

}
