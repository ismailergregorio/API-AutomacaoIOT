package com.example.AutomacaoIOT.Service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.AutomacaoIOT.Model.ModelWebSocket.Mensagem;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicarMensagWebSocket {

    private final SimpMessagingTemplate messagingTemplate;

    public void publicarMensagem(Mensagem mensagem) {
        // System.out.println("Publicando mensagem no app:" + mensagem + "\n");
        messagingTemplate.convertAndSend("/topic/app", mensagem);
    }
}
