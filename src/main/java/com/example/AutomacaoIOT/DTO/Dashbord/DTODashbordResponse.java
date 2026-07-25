package com.example.AutomacaoIOT.DTO.Dashbord;

import java.time.LocalDate;
import java.util.List;

import com.example.AutomacaoIOT.DTO.Widget.DTOWidgetResposeSemDashbord;


public record DTODashbordResponse(
        Long id,
        String nome,
        String descricao,
        List<DTOWidgetResposeSemDashbord> widgets,
        LocalDate dataDeCriacao,
        LocalDate dataDeAtulizacao) {

}
