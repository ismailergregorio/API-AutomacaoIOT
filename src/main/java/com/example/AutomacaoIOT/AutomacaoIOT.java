package com.example.AutomacaoIOT;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.AutomacaoIOT.Config.Security.SecurityConfiguration;
import com.example.AutomacaoIOT.Enun.RolesUser;
import com.example.AutomacaoIOT.Model.ModelDevice.Device;
import com.example.AutomacaoIOT.Model.ModelUser.ModelUser;
import com.example.AutomacaoIOT.Repository.RepositoryDevice;
import com.example.AutomacaoIOT.Repository.RepositoryUser;

import lombok.RequiredArgsConstructor;

@EnableJpaAuditing
@SpringBootApplication
@RequiredArgsConstructor
public class AutomacaoIOT implements CommandLineRunner {


    private final SecurityConfiguration securityConfiguration;

    private final RepositoryUser repositoryUser;

    private final RepositoryDevice repositoryDevice;

    public static void main(String[] args) {
        SpringApplication.run(AutomacaoIOT.class, args);
    }

    @Override
    public void run(String... args) {
        ModelUser user = repositoryUser.findByEmail("padrao@gmail.com");
        if(user != null) return;
        try {

            ModelUser usuario = ModelUser.builder()
                    .nome("Padrão")
                    .sobrenome("Automático")
                    .email("padrao@gmail.com")
                    .senha(securityConfiguration.passwordEncoder().encode("0000"))
                    .telefone("(27)99999-9999")
                    .role(RolesUser.ADMIN)
                    .build();

            repositoryUser.save(usuario);

            System.out.println("Usuário salvo com sucesso!");

            Device dispositivo = Device.builder()
                    .nome("ESP32 Sala")
                    .deviceId("0001")
                    .descricao("Dispositivo padrão do sistema")
                    .placa("ESP32")
                    .ususario(usuario)
                    .build();

            repositoryDevice.save(dispositivo);

            System.out.println("Dispositivo salvo com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}