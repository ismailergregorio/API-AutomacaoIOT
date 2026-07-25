package com.example.AutomacaoIOT.DTO.User;

import com.example.AutomacaoIOT.Enun.RolesUser;

public record DTOUpdateUser(
  String nome,
  String sobrenome,
  String email,
  String telefone,
  RolesUser role,
  Boolean status) {
}
