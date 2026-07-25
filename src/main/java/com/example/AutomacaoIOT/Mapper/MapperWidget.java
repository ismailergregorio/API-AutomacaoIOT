package com.example.AutomacaoIOT.Mapper;

import com.example.AutomacaoIOT.DTO.Dashbord.DTODashbordResponseWidget;
import com.example.AutomacaoIOT.DTO.Widget.DTOWidgetRespose;
import com.example.AutomacaoIOT.DTO.Widget.DTOWidgetResposeSemDashbord;
import com.example.AutomacaoIOT.Model.ModelWidget.ModelWidget;

public class MapperWidget {
  public static DTOWidgetRespose toDTO(ModelWidget w) {
    return new DTOWidgetRespose(
        w.getId(),
        w.getTitulo(),
        w.getTipo(),
        w.getX(),
        w.getY(),
        w.getW(),
        w.getH(),
        w.getOrdem(),
        new DTODashbordResponseWidget(
            w.getDashboard().getId(),
            w.getDashboard().getNome(),
            w.getDashboard().getDescricao()),
        w.getTopicos().stream().map(MapperTopco::toDTO).toList());
  }

  public static DTOWidgetResposeSemDashbord toDTOResposeDashbord(ModelWidget w) {
    return new DTOWidgetResposeSemDashbord(
        w.getId(),
        w.getTitulo(),
        w.getTipo(),
        w.getX(),
        w.getY(),
        w.getW(),
        w.getH(),
        w.getOrdem(),
        MapperDevice.toDTO2(w.getDevice()),
        w.getTopicos().stream().map(MapperTopco::toDTO).toList());
  }
}
