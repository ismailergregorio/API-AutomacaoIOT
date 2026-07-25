package com.example.AutomacaoIOT.Service.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.AutomacaoIOT.DTO.User.DTOGetUserLogin;
import com.example.AutomacaoIOT.DTO.User.DTOResponseAuth;
import com.example.AutomacaoIOT.Model.ModelUser.ModelUser;
import com.example.AutomacaoIOT.Repository.RepositoryUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceAuth implements AuthenticationServices {

    private final RepositoryUser repositoryUser;
    private final TokenServices tokenServices;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        return repositoryUser.findByEmail(email);
    }

    @Override
    public DTOResponseAuth obterToken(DTOGetUserLogin login) {

        ModelUser usuario =
                repositoryUser.findByEmail(login.email());

        if (usuario == null) {
            throw new UsernameNotFoundException(
                    "Usuário não encontrado");
        }

        String token =
                tokenServices.geraToken(usuario);

        String refreshToken =
                tokenServices.gerarRefreshToken(usuario);

        return new DTOResponseAuth(
                token,
                refreshToken);
    }
}