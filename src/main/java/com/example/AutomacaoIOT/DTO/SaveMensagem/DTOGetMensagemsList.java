package com.example.AutomacaoIOT.DTO.SaveMensagem;

import java.time.LocalDateTime;

public record DTOGetMensagemsList(Long id, String deviceId,LocalDateTime dataCriacao, DTOPayLoad payload) {

}
