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

            configurarCallback();

            mqttClient.connect(options);

            subscribe("#");

            System.out.println("===== MQTT =====");
            System.out.println("Cliente: " + mqttClient);
            System.out.println("Conectado: " + mqttClient.isConnected());
            System.out.println("================");
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

        System.out.println("========== PUBLISH ==========");
        System.out.println("Cliente: " + mqttClient);
        System.out.println("Conectado: " + mqttClient.isConnected());
        System.out.println("Tópico: " + topic);
        System.out.println("=============================");
        
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(payload);
            MqttMessage msg = new MqttMessage(json.getBytes());
            msg.setQos(qos);

            mqttClient.publish(topic, msg);
            // System.out.println("Mensagem do App para o Mqtt:" + topic + " " + msg);
        } catch (Exception e) {
            System.out.println("Erro publish");
            e.printStackTrace();
        }
    }

    // =========================
    // CALLBACK
    // =========================
    private void configurarCallback() {
        mqttClient.setCallback(new MqttCallback() {

            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("❌ Conexão perdida: " + cause.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage dados) {
                // System.out.println("📥 Mensagem recebida MQTT:");
                System.out.print(topic);
                System.out.print(dados + "\n\n");

                try {

                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> dadoEsp = mapper.readValue(dados.toString(), Map.class);
                    String deviceId;

                    deviceId = topic.split("/")[1];

                    Payload payload = new Payload();
                    payload.setDeviceId(deviceId);
                    payload.setTopic(topic);
                    payload.setDados(dadoEsp);

                    Mensagem mesagem = new Mensagem();
                    mesagem.setDeviceId(deviceId);
                    mesagem.setPayload(payload);

                    publicarMensag.publicarMensagem(mesagem);

                    sevicesMensagen.postMensagem(new DTOPostMensagem(deviceId, payload.getTopic(), payload.getDados()));
                } catch (Exception e) {
                    System.out.println("Erro ao processar mensagem MQTT");
                    e.printStackTrace();
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                // System.out.println("Entrega concluída");
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
                System.out.println("MQTT Desconectado");
            }
        } catch (Exception e) {
            System.out.println("Erro ao desconectar");
            e.printStackTrace();
        }
    }
}
