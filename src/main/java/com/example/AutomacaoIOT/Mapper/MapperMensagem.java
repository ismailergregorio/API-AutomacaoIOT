package com.example.AutomacaoIOT.Mapper;

import com.example.AutomacaoIOT.DTO.SaveMensagem.DTOGetMensagemsList;
import com.example.AutomacaoIOT.DTO.SaveMensagem.DTOPayLoad;
import com.example.AutomacaoIOT.Model.ModelSaveMensage.SaveMensage;

public class MapperMensagem {
    public static DTOGetMensagemsList toDTO(SaveMensage mensage) {
        return new DTOGetMensagemsList(
                mensage.getId(),
                mensage.getDevice().getDeviceId(),
                mensage.getDataCriacao(),
                new DTOPayLoad(mensage.getDevice().getDeviceId(), mensage.getTopc(), mensage.getDados()));
    }
}
