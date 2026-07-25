package com.example.AutomacaoIOT.Controllers;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.AutomacaoIOT.Config.MqttConfig;
import com.example.AutomacaoIOT.Model.ModelWebSocket.Mensagem;
import com.example.AutomacaoIOT.Model.ModelWebSocket.Payload;
import com.example.AutomacaoIOT.Service.PublicarMensagWebSocket;

@Controller
@RequiredArgsConstructor
public class ControllersWebSocket {
    private final PublicarMensagWebSocket publicarMensag;
    private final MqttConfig mqttConfig;

    @MessageMapping("/teste")
    @CrossOrigin(origins = "*")
    public void receberMensagem(Mensagem mensagem) {

        Payload payload = new Payload();
        Mensagem mensagen = new Mensagem();

        if (mqttConfig.getMqttClient() != null && mqttConfig.getMqttClient().isConnected()) {
            // System.out.println("Mensagem Do Site Recebida: " + mensagem + "\n");
            if (!mensagem.getPayload().getTopic().equals("app/status/mqtt")) {
                mqttConfig.publish(mensagem.getPayload().getTopic(), mensagem.getPayload().getDados());
            } else {
                payload.setDeviceId("servidor");
                payload.setTopic("servidor/conexao/status");
                payload.setDados(Map.of(
                        "conectado", true));
                mensagen.setDeviceId("servidor");
                mensagen.setPayload(payload);

                publicarMensag.publicarMensagem(mensagen);

            }

        } else {

            payload.setDeviceId("servidor");
            payload.setTopic("servidor/conexao/erro");
            payload.setDados(Map.of(
                    "erro", "Servidor MQTT não conectado",
                    "conectado", false));

            mensagen.setDeviceId("servidor");
            mensagen.setPayload(payload);

            publicarMensag.publicarMensagem(mensagen);
            System.out.println("MQTT não conectado. Não foi possível publicar a mensagem.\n");
        }
    }

    @MessageExceptionHandler
    public void handleException(Exception e) {
        System.out.println("Erro ao processar mensagem: " + e.getMessage() + "\n");
    }
}