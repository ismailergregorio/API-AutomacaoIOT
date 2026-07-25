package com.example.AutomacaoIOT.Service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.example.AutomacaoIOT.DTO.Widget.DTOWidgetMover;
import com.example.AutomacaoIOT.DTO.Widget.DTOWidgetPost;
import com.example.AutomacaoIOT.DTO.Widget.DTOWidgetRespose;
import com.example.AutomacaoIOT.Mapper.MapperWidget;
import com.example.AutomacaoIOT.Model.ModelDashbord.ModelDashbord;
import com.example.AutomacaoIOT.Model.ModelDevice.Device;
import com.example.AutomacaoIOT.Model.ModelTopico.ModelTopico;
import com.example.AutomacaoIOT.Model.ModelUser.ModelUser;
import com.example.AutomacaoIOT.Model.ModelWidget.ModelWidget;
import com.example.AutomacaoIOT.Repository.RepositoryDashboard;
import com.example.AutomacaoIOT.Repository.RepositoryDevice;
import com.example.AutomacaoIOT.Repository.RepositoryUser;
import com.example.AutomacaoIOT.Repository.RepositoryWidget;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceWidget {

  private final RepositoryWidget repositoryWidget;
  private final RepositoryDashboard repositoryDashboard;
  private final RepositoryUser repositoryUser;
  private final RepositoryDevice repositoryDevice;

  public DTOWidgetRespose salvar(DTOWidgetPost widget, String email) {

    ModelUser user = repositoryUser.findByEmail(email);

    if (user == null) {
      throw new ResourceAccessException("Usuário não encontrado.");
    }
    Device device = repositoryDevice.findByDeviceId(widget.idDevice())
        .orElseThrow(() -> new ResourceAccessException("Dispositivo não encontrado."));

    ModelDashbord dashboard = repositoryDashboard.findById(widget.idDashbord())
        .orElseThrow(() -> new ResourceAccessException("Dashboard não encontrado."));

    ModelWidget nW = new ModelWidget();
    BeanUtils.copyProperties(widget, nW);

    nW.setDashboard(dashboard);
    nW.setDevice(device);
    nW.getTopicos().addAll(
        widget.topicos().stream()
            .map(t -> {
              ModelTopico topico = new ModelTopico();
              topico.setNome(t.nome());
              topico.setValor(t.valor());
              return topico;
            })
            .toList());

    dashboard.getWidgets().add(nW);
    device.getWidgets().add(nW);
    repositoryWidget.save(nW);

    return MapperWidget.toDTO(nW);
  }

  // --------------------------------------------------
  // Listar Todos
  // --------------------------------------------------

  public List<DTOWidgetRespose> listarTodos() {
    List<ModelWidget> widgets = repositoryWidget.findAll();

    return widgets.stream().map(MapperWidget::toDTO).toList();
  }

  // --------------------------------------------------
  // Buscar por ID
  // --------------------------------------------------

  public DTOWidgetRespose buscarPorId(Long id) {
    ModelWidget w = repositoryWidget.findById(id)
        .orElseThrow(() -> new ResourceAccessException("Widget não encontrado."));
    return MapperWidget.toDTO(w);
  }

  // --------------------------------------------------
  // Buscar por Dashboard
  // --------------------------------------------------

  public List<ModelWidget> buscarPorDashboard(Long dashboardId) {

    return repositoryWidget.findByDashboardId(dashboardId);

  }

  // --------------------------------------------------
  // Buscar por Tipo
  // --------------------------------------------------

  public List<ModelWidget> buscarPorTipo(String tipo) {

    return repositoryWidget.findByTipoIgnoreCase(tipo);

  }

  // --------------------------------------------------
  // Buscar Visíveis
  // --------------------------------------------------

  public List<ModelWidget> buscarVisiveis(Long dashboardId) {

    return repositoryWidget.findByDashboardIdAndVisivelTrue(dashboardId);

  }

  // --------------------------------------------------
  // Buscar por Título
  // --------------------------------------------------

  public List<ModelWidget> buscarPorTitulo(String titulo) {

    return repositoryWidget.findByTituloContainingIgnoreCase(titulo);

  }

  // --------------------------------------------------
  // Atualizar
  // --------------------------------------------------

  public DTOWidgetRespose atualizar(Long id, DTOWidgetPost novo, String email) {
    ModelUser user = repositoryUser.findByEmail(email);

    if (user == null) {
      throw new ResourceAccessException("Usuário não encontrado.");
    }

    ModelWidget widget = repositoryWidget.findById(id)
        .orElseThrow(() -> new ResourceAccessException("Widget não encontrado."));

    BeanUtils.copyProperties(
        novo,
        widget,
        "id",
        "dashboard");

    widget.setTitulo(novo.titulo());
    widget.setTipo(novo.tipo());
    widget.setX(novo.x());
    widget.setY(novo.y());
    widget.setW(novo.w());
    widget.setH(novo.h());
    widget.setOrdem(novo.ordem());
    widget.getTopicos().clear();

    widget.getTopicos().addAll(
        novo.topicos().stream()
            .map(t -> {
              ModelTopico topico = new ModelTopico();
              topico.setNome(t.nome());
              topico.setValor(t.valor());
              return topico;
            })
            .toList());

    return MapperWidget.toDTO(repositoryWidget.save(widget));
  }

  // --------------------------------------------------
  // Excluir
  // --------------------------------------------------

  public void excluir(Long id) {

    ModelWidget widget = repositoryWidget.findById(id)
        .orElseThrow(() -> new ResourceAccessException("Widget não encontrado."));

    repositoryWidget.delete(widget);

  }

  // --------------------------------------------------
  // Mostrar/Ocultar
  // --------------------------------------------------

  public ModelWidget alterarVisibilidade(Long id, Boolean visivel) {

    ModelWidget widget = repositoryWidget.findById(id)
        .orElseThrow(() -> new ResourceAccessException("Widget não encontrado."));

    widget.setVisivel(visivel);

    return repositoryWidget.save(widget);

  }

  // --------------------------------------------------
  // Alterar Ordem
  // --------------------------------------------------

  public ModelWidget mover(String email, Long id, DTOWidgetMover mover) {

    ModelUser user = repositoryUser.findByEmail(email);

    if (user == null) {
      throw new ResourceAccessException("Usuário não encontrado.");
    }

    ModelWidget widget = repositoryWidget.findById(id)
        .orElseThrow(() -> new ResourceAccessException("Widget não encontrado."));

    BeanUtils.copyProperties(mover, widget, "id");

    return repositoryWidget.save(widget);

  }

}