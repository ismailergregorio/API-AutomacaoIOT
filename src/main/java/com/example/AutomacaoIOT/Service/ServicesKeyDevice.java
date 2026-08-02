package com.example.AutomacaoIOT.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.AutomacaoIOT.DTO.KeyDevice.DTOKeyDeviceGet;
import com.example.AutomacaoIOT.DTO.KeyDevice.DTOKeyDevicePost;
import com.example.AutomacaoIOT.Mapper.MapperKeyDevices;
import com.example.AutomacaoIOT.Model.ModelDevice.Device;
import com.example.AutomacaoIOT.Model.ModelKeyDevices.ModelKeyDevices;
import com.example.AutomacaoIOT.Repository.RepositoryDevice;
import com.example.AutomacaoIOT.Repository.RepositoryKeyDevices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicesKeyDevice {

        private final RepositoryKeyDevices repositoryKeyDevices;
        private final RepositoryDevice repositoryDevice;

        public DTOKeyDeviceGet saveKeyDevice(DTOKeyDevicePost dto,String email) {

                Device device = repositoryDevice.findByDeviceId(dto.deviceId())
                                .orElseThrow(() -> new RuntimeException("Dispositivo não encontrado."));

                // Aqui você pode validar se o dispositivo pertence ao usuário utilizando o
                // email

                ModelKeyDevices keyDevice = new ModelKeyDevices();
                keyDevice.setName(dto.name());
                keyDevice.setStatus(true);
                keyDevice.setDevice(device);
                keyDevice.setKey(UUID.randomUUID().toString());

                repositoryKeyDevices.save(keyDevice);

                return MapperKeyDevices.toDTOKeyDeviceGet(keyDevice);
        }

        public DTOKeyDeviceGet updateKeyDevice(Long id, DTOKeyDevicePost dto, String email) {

                ModelKeyDevices keyDevice = repositoryKeyDevices.findById(id)
                                .orElseThrow(() -> new RuntimeException("Chave não encontrada."));

                Device device = repositoryDevice.findByDeviceId(dto.deviceId())
                                .orElseThrow(() -> new RuntimeException("Dispositivo não encontrado."));

                // Aqui você pode validar se o dispositivo pertence ao usuário utilizando o
                // email

                keyDevice.setName(dto.name());
                keyDevice.setDevice(device);

                repositoryKeyDevices.save(keyDevice);

                return MapperKeyDevices.toDTOKeyDeviceGet(keyDevice);
        }

        public void deleteKeyDevice(Long id, String email) {

                ModelKeyDevices keyDevice = repositoryKeyDevices.findById(id)
                                .orElseThrow(() -> new RuntimeException("Chave não encontrada."));

                // Aqui você pode validar se a chave pertence ao usuário utilizando o email

                repositoryKeyDevices.delete(keyDevice);
        }

        public DTOKeyDeviceGet getKeyDevice(Long id) {

                ModelKeyDevices keyDevice = repositoryKeyDevices.findById(id)
                                .orElseThrow(() -> new RuntimeException("Chave não encontrada."));

                return MapperKeyDevices.toDTOKeyDeviceGet(keyDevice);
        }

        public List<DTOKeyDeviceGet> listKeyDevices(String email) {

                // Aqui você pode listar apenas as chaves do usuário autenticado.
                // Caso ainda não tenha esse relacionamento implementado, retorna todas.

                return repositoryKeyDevices.findAll()
                                .stream()
                                .map(MapperKeyDevices::toDTOKeyDeviceGet)
                                .toList();
        }

        public boolean validateKeyDevice(String key) {

                return repositoryKeyDevices.existsByKey(key);
        }

        public String generateKeyDevice() {

                return UUID.randomUUID().toString();
        }
}
