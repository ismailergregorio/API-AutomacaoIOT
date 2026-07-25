package com.example.AutomacaoIOT.DTO.Widget;

import java.util.List;

import com.example.AutomacaoIOT.DTO.Topco.DTOTopcoPost;

public record DTOWidgetPost(
                String idDevice,
                Long idDashbord,
                String titulo,
                String tipo,
                String x,
                String y,
                String w,
                String h,
                String ordem,
                List<DTOTopcoPost> topicos) {
}