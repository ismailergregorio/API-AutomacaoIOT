package com.example.AutomacaoIOT.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AutomacaoIOT.DTO.KeyDevice.DTOKeyDeviceGet;
import com.example.AutomacaoIOT.DTO.KeyDevice.DTOKeyDevicePost;
import com.example.AutomacaoIOT.Service.ServicesKeyDevice;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/key-devices")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ControllerKeyDevices {

    private final ServicesKeyDevice servicesKeyDevice;

    @PostMapping()
    public ResponseEntity<DTOKeyDeviceGet> saveKeyDevice(Authentication authentication, @RequestBody DTOKeyDevicePost dto) {
        String email = authentication.getName();
        System.out.println("Email do usuário autenticado: " + email);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(servicesKeyDevice.saveKeyDevice(dto, email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DTOKeyDeviceGet> updateKeyDevice(
            @PathVariable Long id,
            @RequestBody DTOKeyDevicePost dto,
            Authentication authentication) {

        return ResponseEntity.ok(
                servicesKeyDevice.updateKeyDevice(id, dto, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKeyDevice(@PathVariable Long id,Authentication authentication) {

        servicesKeyDevice.deleteKeyDevice(id, authentication.getName());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTOKeyDeviceGet> getKeyDevice(@PathVariable Long id) {

        return ResponseEntity.ok(
                servicesKeyDevice.getKeyDevice(id));
    }

    @GetMapping()
    public ResponseEntity<List<DTOKeyDeviceGet>> listKeyDevices(
            Authentication authentication) {

        return ResponseEntity.ok(
                servicesKeyDevice.listKeyDevices(authentication.getName()));
    }

    @GetMapping("/validate/{key}")
    public ResponseEntity<Boolean> validateKeyDevice(@PathVariable String key) {

        return ResponseEntity.ok(
                servicesKeyDevice.validateKeyDevice(key));
    }

    @GetMapping("/generate")
    public ResponseEntity<String> generateKeyDevice() {

        return ResponseEntity.ok(
                servicesKeyDevice.generateKeyDevice());
    }
}