package com.example.AutomacaoIOT.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AutomacaoIOT.Model.ModelDashbord.ModelDashbord;

public interface RepositoryDashboard extends JpaRepository<ModelDashbord, Long> {

    List<ModelDashbord> findByUsuarioId(Long usuarioId);

    ModelDashbord findByIdAndUsuarioId(Long id, Long usuarioId);

    Page<ModelDashbord> findByUsuarioId(Long usuarioId, Pageable pageable);

    Optional<ModelDashbord> findByUsuarioIdAndId(Long usuarioId, Long dashboardId);

    List<ModelDashbord> findByNomeContainingIgnoreCase(String nome);

    Page<ModelDashbord> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    boolean existsByUsuarioIdAndNomeIgnoreCase(Long usuarioId, String nome);

    long countByUsuarioId(Long usuarioId);

}