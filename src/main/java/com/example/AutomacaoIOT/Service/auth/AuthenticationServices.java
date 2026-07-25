package com.example.AutomacaoIOT.Service.auth;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.AutomacaoIOT.DTO.User.DTOGetUserLogin;
import com.example.AutomacaoIOT.DTO.User.DTOResponseAuth;

public interface AuthenticationServices extends UserDetailsService{
    DTOResponseAuth obterToken(DTOGetUserLogin login);
}
