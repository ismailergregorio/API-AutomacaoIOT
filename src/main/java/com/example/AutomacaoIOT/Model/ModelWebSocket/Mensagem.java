package com.example.AutomacaoIOT.Model.ModelWebSocket;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class Mensagem {
    private String deviceId;
    private Payload payload;
    // getters e setters
}