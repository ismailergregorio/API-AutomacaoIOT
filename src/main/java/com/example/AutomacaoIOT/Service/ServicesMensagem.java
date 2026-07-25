package com.example.AutomacaoIOT.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.AutomacaoIOT.DTO.SaveMensagem.DTOGetMensagems;
import com.example.AutomacaoIOT.DTO.SaveMensagem.DTOGetMensagemsList;
import com.example.AutomacaoIOT.DTO.SaveMensagem.DTOPayLoad;
import com.example.AutomacaoIOT.DTO.SaveMensagem.DTOPostMensagem;
import com.example.AutomacaoIOT.Mapper.MapperMensagem;
import com.example.AutomacaoIOT.Model.ModelDevice.Device;
import com.example.AutomacaoIOT.Model.ModelSaveMensage.SaveMensage;
import com.example.AutomacaoIOT.Repository.RepositoryDevice;
import com.example.AutomacaoIOT.Repository.RepositoryMensages;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicesMensagem {

    private final RepositoryMensages repositoriyMensages;

    private final RepositoryDevice repositoryDevice;

    public String postMensagem(DTOPostMensagem mensagem) {

        Optional<Device> device = repositoryDevice.findByDeviceId(mensagem.deviceId());

        if (!device.isPresent()) {
            return "DeviceId não esta cadastrado";
        }

        SaveMensage novaMesagem = new SaveMensage();

        novaMesagem.setDevice(device.get());
        novaMesagem.setTopc(mensagem.topc());
        novaMesagem.setDados(mensagem.dados());

        repositoriyMensages.save(novaMesagem);

        return "Mensagem salva";
    }

    public List<DTOGetMensagemsList> getTopcosMensagem(String email, String topico) {
        List<SaveMensage> listaMensagem = repositoriyMensages.findTop50ByTopcOrderByIdDesc(topico);

        return listaMensagem.stream().map(MapperMensagem::toDTO).toList();
    }

    public List<DTOGetMensagems> getMensagens(String deviceId) {
        Optional<Device> device = repositoryDevice.findByDeviceId(deviceId);

        if (!device.isPresent()) {
            return new ArrayList<>();
        }

        // List<SaveMensage> mesagens = repositoriyMensages.findByDevice(device.get());
        List<SaveMensage> mesagens = repositoriyMensages.findTop50ByDeviceOrderByIdDesc(device.get());
        List<DTOGetMensagems> dados = mesagens.stream()
                .map(m -> new DTOGetMensagems(m.getDevice().getDeviceId(),
                        new DTOPayLoad(m.getDevice().getDeviceId(), m.getTopc(), m.getDados())))
                .toList();

        return dados;
    }

    public List<DTOGetMensagems> getAllMensagems() {
        List<SaveMensage> allMensagem = repositoriyMensages.findFirst100ByOrderByIdDesc();

        List<DTOGetMensagems> mensagens = allMensagem.stream().map(m -> new DTOGetMensagems(
                m.getDevice().getDeviceId(), new DTOPayLoad(m.getDevice().getDeviceId(), m.getTopc(), m.getDados())))
                .toList();
        return mensagens;
    }
}