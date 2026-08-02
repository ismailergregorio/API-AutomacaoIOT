package com.example.AutomacaoIOT.Model.ModelKeyDevices;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.AutomacaoIOT.Model.ModelDevice.Device;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_key_devices")
@EntityListeners(AuditingEntityListener.class)
public class ModelKeyDevices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "boolean default true")
    private Boolean status;

    @Column(nullable = false, unique = true)
    private String key;

    @OneToOne
    @JoinColumn(referencedColumnName = "id",nullable = false, unique = true)
    private Device device;

    @Column(nullable = false, unique = true)
    @CreatedDate
    private LocalDateTime dataDeCriacao;

    @LastModifiedDate
    private LocalDateTime dataDeAtualizacao;
}
