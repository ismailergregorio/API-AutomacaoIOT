package com.example.AutomacaoIOT.Mapper;

import com.example.AutomacaoIOT.DTO.Dashbord.DTODashbordResponse;
import com.example.AutomacaoIOT.Model.ModelDashbord.ModelDashbord;

public class MapperDashbord {
    public static DTODashbordResponse toDTO(ModelDashbord modelDashbord) {
        return new DTODashbordResponse(
                modelDashbord.getId(),
                modelDashbord.getNome(),
                modelDashbord.getDescricao(),
                modelDashbord.getWidgets().stream().map(MapperWidget::toDTOResposeDashbord).toList(),
                modelDashbord.getDataDeCriacao(),
                modelDashbord.getDataDeAtulizacao());
    }
}
