package com.example.AutomacaoIOT.DTO.User;

public record DTOGetUser(
        Long id,
        String email,
        String nome,
        String sobrenome,
        Boolean status,
        String telefone,
        String roles) {

}
