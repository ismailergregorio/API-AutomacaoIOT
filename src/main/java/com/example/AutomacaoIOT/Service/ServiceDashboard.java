package com.example.AutomacaoIOT.Service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.example.AutomacaoIOT.DTO.Dashbord.DTODashbord;
import com.example.AutomacaoIOT.DTO.Dashbord.DTODashbordResponse;
import com.example.AutomacaoIOT.Mapper.MapperDashbord;
import com.example.AutomacaoIOT.Model.ModelDashbord.ModelDashbord;
import com.example.AutomacaoIOT.Model.ModelUser.ModelUser;
import com.example.AutomacaoIOT.Repository.RepositoryDashboard;
import com.example.AutomacaoIOT.Repository.RepositoryUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceDashboard {

    private final RepositoryDashboard repositoryDashboard;
    private final RepositoryUser repositoryUser;

    // ----------------------------------------------------
    // Salvar
    // ----------------------------------------------------

    public DTODashbordResponse salvar(DTODashbord dashboard, String email) {
        ModelUser usuario = repositoryUser.findByEmail(email);

        if (usuario == null) {
            throw new ResourceAccessException("Usuário não encontrado.");
        }

        if (repositoryDashboard.existsByUsuarioIdAndNomeIgnoreCase(
                usuario.getId(),
                dashboard.nome())) {

            throw new RuntimeException("Já existe um dashboard com esse nome.");
        }

        ModelDashbord novoDashboard = new ModelDashbord();
        novoDashboard.setNome(dashboard.nome());
        novoDashboard.setDescricao(dashboard.descricao());
        novoDashboard.setUsuario(usuario);

        return MapperDashbord.toDTO(repositoryDashboard.save(novoDashboard));
    }

    // ----------------------------------------------------
    // Listar
    // ----------------------------------------------------

    public List<DTODashbordResponse> listarTodos() {
        return repositoryDashboard.findAll().stream()
                .map(MapperDashbord::toDTO)
                .toList();
    }

    // ----------------------------------------------------
    // Paginação
    // ----------------------------------------------------

    public Page<ModelDashbord> listar(Pageable pageable) {
        return repositoryDashboard.findAll(pageable);
    }

    // ----------------------------------------------------
    // Buscar por ID
    // ----------------------------------------------------

    public DTODashbordResponse buscarPorId(Long id, String email) {
        ModelUser usuario = repositoryUser.findByEmail(email);

        if (usuario == null) {
            throw new ResourceAccessException("Usuário não encontrado.");
        }

        ModelDashbord dados = repositoryDashboard.findByIdAndUsuarioId(id, usuario.getId());
        if (dados == null) {
            throw new ResourceAccessException("Dashboard não encontrado.");
        }
        ;

        return MapperDashbord.toDTO(dados);
    }

    // ----------------------------------------------------
    // Buscar por Usuário
    // ----------------------------------------------------

    public List<DTODashbordResponse> buscarMeusDashbords(String email) {
        ModelUser usuario = repositoryUser.findByEmail(email);

        if (usuario == null) {
            throw new ResourceAccessException("Usuário não encontrado.");
        }

        return repositoryDashboard.findByUsuarioId(usuario.getId()).stream()
                .map(MapperDashbord::toDTO)
                .toList();
    }

    // ----------------------------------------------------
    // Buscar por Nome
    // ----------------------------------------------------

    public List<ModelDashbord> buscarPorNome(String nome) {

        return repositoryDashboard.findByNomeContainingIgnoreCase(nome);

    }

    // ----------------------------------------------------
    // Atualizar
    // ----------------------------------------------------

    public DTODashbordResponse atualizar(Long id, DTODashbordResponse novo, String email) {

        // DTODashbordResponse dashboard = buscarPorId(id, email);

        ModelDashbord dashboard = repositoryDashboard.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Dashboard não encontrado."));

        BeanUtils.copyProperties(
                novo,
                dashboard,
                "id",
                "usuario",
                "dataDeCriacao",
                "widgets");

        return MapperDashbord.toDTO(repositoryDashboard.save(dashboard));

    }

    // ----------------------------------------------------
    // Excluir
    // ----------------------------------------------------

    public void excluir(Long id) {

    // ModelDashbord dashboard = buscarPorId(id);
    ModelDashbord dashboard = repositoryDashboard.findById(id)
            .orElseThrow(() -> new ResourceAccessException("Dashboard não encontrado."));

    repositoryDashboard.delete(dashboard);

    }

    // ----------------------------------------------------
    // Contagem
    // ----------------------------------------------------

    public Long quantidadeUsuario(Long usuarioId) {

        return repositoryDashboard.countByUsuarioId(usuarioId);

    }

    // ----------------------------------------------------
    // Existe
    // ----------------------------------------------------

    public boolean existe(Long id) {

        return repositoryDashboard.existsById(id);

    }

}