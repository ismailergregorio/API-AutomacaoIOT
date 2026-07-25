package com.example.AutomacaoIOT.Model.ModelDevice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.AutomacaoIOT.Model.ModelSaveMensage.SaveMensage;
import com.example.AutomacaoIOT.Model.ModelUser.ModelUser;
import com.example.AutomacaoIOT.Model.ModelWidget.ModelWidget;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbDevice")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String deviceId;

    private String descricao;

    private String placa;

    @CreatedDate
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    private LocalDateTime dataAtulisacao;

    @ManyToOne()
    @JoinColumn(name = "id_usuario")
    private ModelUser ususario;

    @Builder.Default
    @OneToMany(mappedBy = "device")
    private List<SaveMensage> mensagens = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ModelWidget> widgets = new ArrayList<>();
}