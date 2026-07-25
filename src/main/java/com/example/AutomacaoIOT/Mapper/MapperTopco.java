package com.example.AutomacaoIOT.Mapper;

import com.example.AutomacaoIOT.DTO.Topco.DTOTopcoRespose;
import com.example.AutomacaoIOT.Model.ModelTopico.ModelTopico;

public class MapperTopco {
 public static DTOTopcoRespose toDTO(ModelTopico topco) {
  return new DTOTopcoRespose(
    topco.getId(),
    topco.getNome(),
    topco.getValor());
 }
}
