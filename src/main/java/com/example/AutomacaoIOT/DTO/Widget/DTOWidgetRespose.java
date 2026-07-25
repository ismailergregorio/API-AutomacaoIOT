package com.example.AutomacaoIOT.DTO.Widget;

import java.util.List;

import com.example.AutomacaoIOT.DTO.Dashbord.DTODashbordResponseWidget;
import com.example.AutomacaoIOT.DTO.Topco.DTOTopcoRespose;

public record DTOWidgetRespose(
        Long id,
        String titulo,
        String tipo,
        String x,
        String y,
        String w,
        String h,
        String ordem,
        DTODashbordResponseWidget dashbord,
        List<DTOTopcoRespose> topicos) {
}