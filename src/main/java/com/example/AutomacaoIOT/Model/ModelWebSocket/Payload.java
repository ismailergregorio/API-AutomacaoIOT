package com.example.AutomacaoIOT.Model.ModelWebSocket;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Payload {
    private String deviceId;
    private String topic;
    private Map<String, Object> dados;
}