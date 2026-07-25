package com.example.AutomacaoIOT.DTO.SaveMensagem;

import java.util.Map;

public record DTOPayLoad(String deviceId,String topic, Map<String, Object> dados) {
    
}
