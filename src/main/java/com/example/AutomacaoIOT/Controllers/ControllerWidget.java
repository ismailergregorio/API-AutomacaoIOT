package com.example.AutomacaoIOT.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.AutomacaoIOT.DTO.Widget.DTOWidgetMover;
import com.example.AutomacaoIOT.DTO.Widget.DTOWidgetPost;
import com.example.AutomacaoIOT.DTO.Widget.DTOWidgetRespose;
import com.example.AutomacaoIOT.Model.ModelWidget.ModelWidget;
import com.example.AutomacaoIOT.Service.ServiceWidget;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/widget")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ControllerWidget {

    private final ServiceWidget serviceWidget;

    @PostMapping()
    public ResponseEntity<?> salvar(
            Authentication authentication,
            @RequestBody DTOWidgetPost widget) {

        String email = authentication.getName();
        System.out.print(widget);
        serviceWidget.salvar(widget, email);

        return ResponseEntity.status(HttpStatus.CREATED).body("ok");

    }

    @GetMapping
    public ResponseEntity<List<DTOWidgetRespose>> listarTodos() {

        return ResponseEntity.ok(serviceWidget.listarTodos());

    }

    @GetMapping("/{id}")
    public ResponseEntity<DTOWidgetRespose> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(serviceWidget.buscarPorId(id));

    }

    @GetMapping("/dashboard/{id}")
    public ResponseEntity<List<ModelWidget>> buscarDashboard(
            @PathVariable Long id) {

        return ResponseEntity.ok(serviceWidget.buscarPorDashboard(id));

    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ModelWidget>> buscarTipo(
            @PathVariable String tipo) {

        return ResponseEntity.ok(serviceWidget.buscarPorTipo(tipo));

    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<ModelWidget>> buscarTitulo(
            @PathVariable String titulo) {

        return ResponseEntity.ok(serviceWidget.buscarPorTitulo(titulo));

    }

    @GetMapping("/visiveis/{dashboard}")
    public ResponseEntity<List<ModelWidget>> buscarVisiveis(
            @PathVariable Long dashboard) {

        return ResponseEntity.ok(serviceWidget.buscarVisiveis(dashboard));

    }

    @PutMapping("/{id}")
    public ResponseEntity<DTOWidgetRespose> atualizar(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody DTOWidgetPost widget) {
        System.out.print(widget);
        return ResponseEntity.ok(serviceWidget.atualizar(id, widget, authentication.getName()));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        serviceWidget.excluir(id);

        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/{id}/visibilidade")
    public ResponseEntity<ModelWidget> alterarVisibilidade(
            @PathVariable Long id,
            @RequestParam Boolean visivel) {

        return ResponseEntity.ok(
                serviceWidget.alterarVisibilidade(id, visivel));

    }

    @PutMapping("/mover/{id}")
    public ResponseEntity<ModelWidget> alterarOrdem(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody DTOWidgetMover ordem) {

        String email = authentication.getName();
        return ResponseEntity.ok(
                serviceWidget.mover(email, id, ordem));

    }

}