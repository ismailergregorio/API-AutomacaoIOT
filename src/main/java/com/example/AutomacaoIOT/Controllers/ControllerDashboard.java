package com.example.AutomacaoIOT.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.AutomacaoIOT.DTO.Dashbord.DTODashbord;
import com.example.AutomacaoIOT.DTO.Dashbord.DTODashbordResponse;
import com.example.AutomacaoIOT.Service.ServiceDashboard;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ControllerDashboard {

    private final ServiceDashboard serviceDashboard;

    @PostMapping
    public ResponseEntity<DTODashbordResponse> salvar(Authentication authentication,
            @RequestBody DTODashbord dashboard) {
        String email = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(serviceDashboard.salvar(dashboard, email));
    }

    @GetMapping
    public ResponseEntity<List<DTODashbordResponse>> listarTodos() {
        return ResponseEntity.ok(serviceDashboard.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTODashbordResponse> buscarPorId(Authentication authentication,
            @PathVariable Long id) {
        String email = authentication.getName();
        return ResponseEntity.ok(serviceDashboard.buscarPorId(id, email));

    }


    @GetMapping("/my-dashbord")
    public ResponseEntity<List<DTODashbordResponse>> buscarMeusDashbords(
            Authentication authentication) {
        String email = authentication.getName();

        return ResponseEntity.ok(serviceDashboard.buscarMeusDashbords(email));

    }

    @PutMapping("/{id}")
    public ResponseEntity<DTODashbordResponse> atualizar(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody DTODashbordResponse dashboard) {
        String email = authentication.getName();

        return ResponseEntity.ok(serviceDashboard.atualizar(id, dashboard, email));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        serviceDashboard.excluir(id);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/quantidade/{usuario}")
    public ResponseEntity<Long> quantidade(
            @PathVariable Long usuario) {

        return ResponseEntity.ok(serviceDashboard.quantidadeUsuario(usuario));

    }

}