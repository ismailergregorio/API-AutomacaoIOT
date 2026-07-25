package com.example.AutomacaoIOT.DTO.Widget;

import java.util.List;

import com.example.AutomacaoIOT.DTO.Device.DtoRespostaDevicesSemDashbord;
import com.example.AutomacaoIOT.DTO.Topco.DTOTopcoRespose;

public record DTOWidgetResposeSemDashbord(
        Long id,
        String titulo,
        String tipo,
        String x,
        String y,
        String w,
        String h,
        String ordem,
        DtoRespostaDevicesSemDashbord device,
        List<DTOTopcoRespose> topicos) {
}