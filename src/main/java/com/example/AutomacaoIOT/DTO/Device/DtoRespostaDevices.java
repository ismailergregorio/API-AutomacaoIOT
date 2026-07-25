package com.example.AutomacaoIOT.DTO.Device;

import java.util.List;

import com.example.AutomacaoIOT.DTO.Widget.DTOWidgetRespose;

public record DtoRespostaDevices(
  Long id,
  String deviceId,
  String nome,
  String descricao,
  String placa,
  List<DTOWidgetRespose> widgets) {

}
