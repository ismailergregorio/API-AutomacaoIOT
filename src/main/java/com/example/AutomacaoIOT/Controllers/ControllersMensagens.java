package com.example.AutomacaoIOT.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AutomacaoIOT.DTO.SaveMensagem.DTOGetMensagems;
import com.example.AutomacaoIOT.DTO.SaveMensagem.DTOGetMensagemsList;
import com.example.AutomacaoIOT.Service.ServicesMensagem;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/mensagem")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ControllersMensagens {
    private final ServicesMensagem servicesMensagem;

    @GetMapping("/{deviceId}")
    private ResponseEntity<?> getMensagens(@PathVariable String deviceId) {

        return ResponseEntity.ok(servicesMensagem.getMensagens(deviceId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DTOGetMensagems>> getAllMensagens() {
        return ResponseEntity.ok(servicesMensagem.getAllMensagems());
    }

    @GetMapping("/topc")
    public List<DTOGetMensagemsList> getMethodName(Authentication authentication, @RequestParam String topco) {
        System.out.println("Chegou ============================="+topco);
        String email = authentication.getName();
        return servicesMensagem.getTopcosMensagem(email, topco);
    }

}
