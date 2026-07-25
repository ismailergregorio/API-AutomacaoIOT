package com.example.AutomacaoIOT.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AutomacaoIOT.Model.ModelWidget.ModelWidget;

public interface RepositoryWidget extends JpaRepository<ModelWidget, Long> {

    List<ModelWidget> findByDashboardId(Long dashboardId);

    Page<ModelWidget> findByDashboardId(Long dashboardId, Pageable pageable);

    List<ModelWidget> findByDashboardIdOrderByOrdem(Long dashboardId);

    List<ModelWidget> findByDashboardIdAndVisivelTrue(Long dashboardId);

    List<ModelWidget> findByTipo(String tipo);

    List<ModelWidget> findByTipoIgnoreCase(String tipo);

    List<ModelWidget> findByTituloContainingIgnoreCase(String titulo);

    // List<ModelWidget> findByLinha(Integer linha);

    // List<ModelWidget> findByColuna(Integer coluna);

    // List<ModelWidget> findByLinhaAndColuna(Integer linha, Integer coluna);

    // boolean existsByDashboardIdAndLinhaAndColuna(Long dashboardId,
    //                                              Integer linha,
    //                                              Integer coluna);

    long countByDashboardId(Long dashboardId);

}