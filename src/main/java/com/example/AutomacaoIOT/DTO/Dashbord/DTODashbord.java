package com.example.AutomacaoIOT.DTO.Dashbord;

import java.util.List;

import com.example.AutomacaoIOT.Model.ModelWidget.ModelWidget;

public record DTODashbord(
        String nome,
        String descricao,
        List<ModelWidget> widgets) {

}