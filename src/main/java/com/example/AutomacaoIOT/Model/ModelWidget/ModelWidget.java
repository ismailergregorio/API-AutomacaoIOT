package com.example.AutomacaoIOT.Model.ModelWidget;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.AutomacaoIOT.Model.ModelDashbord.ModelDashbord;
import com.example.AutomacaoIOT.Model.ModelDevice.Device;
import com.example.AutomacaoIOT.Model.ModelTopico.ModelTopico;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "DBwidget")
@EntityListeners(AuditingEntityListener.class)
public class ModelWidget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String tipo;

    private String x;

    private String y;

    private String w;

    private String h;

    private String ordem;

    private Boolean visivel = true;

    @CreatedDate
    private LocalDate dataDeCriacao;

    @LastModifiedDate
    private LocalDate dataDeAtulizacao;

    @ManyToOne()
    @JoinColumn(name = "id_dashnord")
    private ModelDashbord dashboard;

    @ManyToOne()
    @JoinColumn(name = "id_device")
    private Device device;

    @OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL ,orphanRemoval = true)
    @CollectionTable(name = "widget_topicos", joinColumns = @JoinColumn(name = "widget_id"))
    private List<ModelTopico> topicos = new ArrayList<>();
}