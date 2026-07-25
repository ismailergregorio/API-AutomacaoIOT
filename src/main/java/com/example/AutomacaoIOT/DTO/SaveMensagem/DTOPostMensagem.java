package com.example.AutomacaoIOT.DTO.SaveMensagem;

import java.util.Map;

public record DTOPostMensagem(String deviceId,String topc, Map<String, Object> dados) {
    
}
