package com.example.AutomacaoIOT.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.AutomacaoIOT.DTO.Device.DtoCreateDevice;
import com.example.AutomacaoIOT.DTO.Device.DtoRespostaDevices;
import com.example.AutomacaoIOT.DTO.Device.DtoUpdateDevice;
import com.example.AutomacaoIOT.Mapper.MapperDevice;
import com.example.AutomacaoIOT.Model.ModelDevice.Device;
import com.example.AutomacaoIOT.Model.ModelUser.ModelUser;
import com.example.AutomacaoIOT.Repository.RepositoryDevice;
import com.example.AutomacaoIOT.Repository.RepositoryUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicesDevice {
    private final RepositoryDevice repository;
    private final RepositoryUser repositoryUser;

    public ResponseEntity<?> CreateDevice(DtoCreateDevice device, String email) {
        ModelUser user = repositoryUser.findByEmail(email);

        if(!user.isEnabled()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario Não Encotrado");
        }

        if (repository.findByDeviceIdAndUsusario(device.deviceId(),user).isPresent()) {
            System.out.print("Dispositivo ja Existe");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Dispositivo Já Existe");
        }

        Device novoDispositivo = new Device();

        novoDispositivo.setNome(device.nome());
        novoDispositivo.setDeviceId(device.deviceId());
        novoDispositivo.setDescricao(device.descricao());
        novoDispositivo.setPlaca(device.placa());
        novoDispositivo.setUsusario(user);

        repository.save(novoDispositivo);
        return ResponseEntity.status(HttpStatus.CREATED).body(MapperDevice.toDTO(novoDispositivo));
    }

    public ResponseEntity<?> DeleteDevice(String deviceId,String email) {
        ModelUser user = repositoryUser.findByEmail(email);

        Optional<Device> dispositivo = repository.findByDeviceIdAndUsusario(deviceId,user);

        if (!dispositivo.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dispositivo Não Encotrado");
        }
        
        repository.delete(dispositivo.get());
        return ResponseEntity.status(HttpStatus.OK).body("Dispositivo deletado com susseso");
    }

    public ResponseEntity<?> UpdateDevice(DtoUpdateDevice dados,String email) {
        ModelUser user = repositoryUser.findByEmail(email);

        Optional<Device> dispositivo = repository.findByDeviceIdAndUsusario(dados.deviceId(),user);

        if (!dispositivo.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dispositivo Não Encotrado");
        }

        Device atulizarDevice = dispositivo.get();

        atulizarDevice.setNome(dados.nome());
        atulizarDevice.setDeviceId(dados.deviceId());
        atulizarDevice.setDescricao(dados.descricao());

        repository.save(atulizarDevice);

        return ResponseEntity.status(HttpStatus.OK).body(dados);
    }

    public ResponseEntity<?> Device(String device,String email) {
        ModelUser user = repositoryUser.findByEmail(email);

        Optional<Device> dispositivo = repository.findByDeviceIdAndUsusario(device,user);

        if (!dispositivo.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dispositivo Não Encotrado");
        }

        Device dEncomtrado = dispositivo.get();
        return ResponseEntity.ok(MapperDevice.toDTO(dEncomtrado));
    }

    public ResponseEntity<?> Devices(String email) {
        ModelUser user =  repositoryUser.findByEmail(email);

        List<DtoRespostaDevices> devices = repository.findByUsusario(user).stream()
                .map(device -> MapperDevice.toDTO(device))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(devices);
    }

}
