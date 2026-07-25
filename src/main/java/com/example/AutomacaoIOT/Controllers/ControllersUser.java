package com.example.AutomacaoIOT.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.AutomacaoIOT.DTO.User.DTOGetUser;
import com.example.AutomacaoIOT.DTO.User.DTOPostUser;
import com.example.AutomacaoIOT.DTO.User.DTOUpdateUser;
import com.example.AutomacaoIOT.Model.ModelUser.ModelUser;
import com.example.AutomacaoIOT.Service.ServicesUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class ControllersUser {
    private final ServicesUser servicesUser;

    @PostMapping
    public ResponseEntity<?> CriarUser(@RequestBody DTOPostUser user) {

        servicesUser.crearUser(user);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<DTOGetUser>> ListaUser() {
        return ResponseEntity.ok(
                servicesUser.getListaUser());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModelUser> AtualizarUser(
            @PathVariable Long id,
            @RequestBody DTOUpdateUser user) {

        return ResponseEntity.ok(
                servicesUser.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeletarUser(
            @PathVariable Long id) {
        servicesUser.deleteUser(id);

        return ResponseEntity.ok().build();
    }

    // @PatchMapping("/{id}/status")
    // public ResponseEntity<ModelUser> AlterarStatus(
    //         @PathVariable Long id) {

    //     return ResponseEntity.ok(
    //             servicesUser.alterarStatus(id));
    // }
}