package com.example.AutomacaoIOT.Model.ModelDashbord;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.AutomacaoIOT.Model.ModelUser.ModelUser;
import com.example.AutomacaoIOT.Model.ModelWidget.ModelWidget;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@Table(name = "DBdashboard")
@EntityListeners(AuditingEntityListener.class)
public class ModelDashbord {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private String nome;

 @Column(length = 500)
 private String descricao;

 @CreatedDate
 private LocalDate dataDeCriacao;

 @LastModifiedDate
 private LocalDate dataDeAtulizacao;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "usuario_id")
 private ModelUser usuario;

 @OneToMany(mappedBy = "dashboard", cascade = CascadeType.ALL, orphanRemoval = true)
 private List<ModelWidget> widgets = new ArrayList<>();
}
