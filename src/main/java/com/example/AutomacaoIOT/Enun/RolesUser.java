package com.example.AutomacaoIOT.Enun;

import lombok.Getter;

@Getter
public enum RolesUser{

    ADMIN("ADMIN"),
    OPERADOR("OPERADOR"),
    VISUALIZADOR("VISUALIZADOR");

    private final String role;

    RolesUser(String role){
        this.role=role; 
    }
}