package com.example.AutomacaoIOT.Config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.AutomacaoIOT.DTO.SaveMensagem.DTOPostMensagem;
import com.example.AutomacaoIOT.Model.ModelWebSocket.Mensagem;
import com.example.AutomacaoIOT.Model.ModelWebSocket.Payload;
import com.example.AutomacaoIOT.Service.PublicarMensagWebSocket;
import com.example.AutomacaoIOT.Service.ServicesMensagem;

@Service
@Getter
@RequiredArgsConstructor
public class MqttConfig {

    @Value("${mqtt.broker}")
    private String broker;

    @Value("${mqtt.client-id}")
    private String clientId;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Value("${mqtt.qos}")
    private int qos;

    private MqttClient mqttClient;

    private final SimpMessagingTemplate messagingTemplate;

    private final PublicarMensagWebSocket publicarMensag;

    private final ServicesMensagem sevicesMensagen;

    private final ObjectMapper mapper = new ObjectMapper();

    // =========================
    // CONECTA AUTOMATICAMENTE
    // =========================
    @PostConstruct
    public void conectar() {
        try {

            mqttClient = new MqttClient(broker, clientId);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());

            options.setAutomaticReconnect(true);
            options.setCleanSession(true);

            options.setKeepAliveInterval(30);
            options.setConnectionTimeout(15);
            options.setMaxInflight(100);

            configurarCallback();

            mqttClient.connect(options);

            System.out.println("==================================");
            System.out.println("MQTT conectado");
            System.out.println("Broker : " + broker);
            System.out.println("Client : " + clientId);
            System.out.println("==================================");

        } catch (Exception e) {

            System.out.println("Erro ao conectar MQTT");
            e.printStackTrace();

        }
    }

    // =========================
    // SUBSCRIBE
    // =========================
    public void subscribe(String topic) {
        try {
            mqttClient.subscribe(topic, qos);
            // System.out.println("Inscrito em: " + topic);
        } catch (Exception e) {
            System.out.println("Erro subscribe");
            e.printStackTrace();
        }
    }

    // =========================
    // PUBLISH
    // =========================
    public void publish(String topic, Object payload) {

        try {

            if (mqttClient == null) {

                System.out.println("MQTT Client é NULL.");
                return;

            }

            if (!mqttClient.isConnected()) {

                System.out.println("MQTT desconectado. Não foi possível publicar.");

                return;

            }

            String json = mapper.writeValueAsString(payload);

            MqttMessage msg = new MqttMessage(json.getBytes());

            msg.setQos(qos);

            mqttClient.publish(topic, msg);

            System.out.println("==================================");
            System.out.println("Mensagem publicada");
            System.out.println("Topico : " + topic);
            System.out.println("Payload: " + json);
            System.out.println("==================================");

        } catch (Exception e) {

            System.out.println("Erro ao publicar");

            e.printStackTrace();

        }

    }

    // =========================
    // CALLBACK
    // =========================
    private void configurarCallback() {

        mqttClient.setCallback(new MqttCallbackExtended() {

            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

                System.out.println("==================================");
                System.out.println("MQTT conectado");
                System.out.println("Reconnect : " + reconnect);
                System.out.println("ServerURI : " + serverURI);

                if (mqttClient == null) {
                    return;
                }

                if (!mqttClient.isConnected()) {
                    System.out.println("Cliente ainda não está conectado.");
                    return;
                }

                try {

                    mqttClient.unsubscribe("#"); // opcional

                } catch (Exception ignored) {
                }

                try {

                    mqttClient.subscribe("#", qos);

                    System.out.println("Subscribe realizado.");

                } catch (Exception e) {

                    e.printStackTrace();

                }

                System.out.println("==================================");
            }

            @Override
            public void connectionLost(Throwable cause) {

                System.out.println("==================================");
                System.out.println("MQTT CONNECTION LOST");

                if (cause != null) {

                    System.out.println("Classe : " + cause.getClass().getName());
                    System.out.println("Mensagem: " + cause.getMessage());

                    if (cause instanceof MqttException ex) {

                        System.out.println("ReasonCode: " + ex.getReasonCode());

                    }

                    cause.printStackTrace();

                }

                System.out.println("==================================");

            }

            @Override
            public void messageArrived(String topic, MqttMessage dados) {

                try {

                    System.out.println("MQTT <- " + topic);

                    Map<String, Object> dadoEsp = mapper.readValue(dados.toString(), Map.class);

                    String deviceId = topic.split("/")[1];

                    Payload payload = new Payload();

                    payload.setDeviceId(deviceId);
                    payload.setTopic(topic);
                    payload.setDados(dadoEsp);

                    Mensagem mensagem = new Mensagem();

                    mensagem.setDeviceId(deviceId);
                    mensagem.setPayload(payload);

                    publicarMensag.publicarMensagem(mensagem);

                    sevicesMensagen.postMensagem(
                            new DTOPostMensagem(
                                    deviceId,
                                    payload.getTopic(),
                                    payload.getDados()));

                } catch (Exception e) {

                    e.printStackTrace();

                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

                // opcional

            }

        });

    }

    // =========================
    // DESCONECTAR AO FINALIZAR APP
    // =========================
    @PreDestroy
    public void desconectar() {

        try {

            if (mqttClient != null && mqttClient.isConnected()) {

                mqttClient.disconnect();

                mqttClient.close();

                System.out.println("MQTT Desconectado");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}
