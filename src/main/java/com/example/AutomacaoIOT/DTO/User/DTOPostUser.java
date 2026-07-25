package com.example.AutomacaoIOT.DTO.User;

import com.example.AutomacaoIOT.Enun.RolesUser;

public record DTOPostUser(
        String email,
        String senha,
        String nome,
        String sobrenome,
        String telefone,
        RolesUser role) {

}
