package com.example.AutomacaoIOT.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AutomacaoIOT.DTO.User.DTOGetUserLogin;
import com.example.AutomacaoIOT.DTO.User.DTORefresh;
import com.example.AutomacaoIOT.DTO.User.DTOResponseAuth;
import com.example.AutomacaoIOT.DTO.User.DTOResponseAuthRefresh;
import com.example.AutomacaoIOT.Model.ModelUser.ModelUser;
import com.example.AutomacaoIOT.Repository.RepositoryUser;
import com.example.AutomacaoIOT.Service.auth.ServiceAuth;
import com.example.AutomacaoIOT.Service.auth.TokenServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ControllersAuth {
    private final RepositoryUser repositoryUser;

    private final TokenServices tokenServices;

    private final AuthenticationManager authenticationManager;

    private final ServiceAuth serviceAuth;

    @PostMapping("/login")
    public ResponseEntity<DTOResponseAuth> login(
            @RequestBody DTOGetUserLogin dto) {

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                dto.email(),
                dto.senha());

        authenticationManager.authenticate(auth);

        DTOResponseAuth token = serviceAuth.obterToken(dto);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<DTOResponseAuthRefresh> login(
            @RequestBody DTORefresh dto) {

        String email = tokenServices.validarRefreshToken(dto.refreshToken());

        ModelUser usuario = repositoryUser.findByEmail(email);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        String novoAccess = tokenServices.geraToken(usuario);

        String novoRefresh = tokenServices.gerarRefreshToken(usuario);

        return ResponseEntity.ok(new DTOResponseAuthRefresh(novoAccess, novoRefresh));
    }
}