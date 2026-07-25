package com.example.AutomacaoIOT.Model.ModelUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.AutomacaoIOT.Enun.RolesUser;
import com.example.AutomacaoIOT.Model.ModelDevice.Device;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dbUsuario")
@EntityListeners(AuditingEntityListener.class)
@Builder
public class ModelUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String email;
    private String senha;
    private String nome;
    private String sobrenome;
    @Builder.Default
    private Boolean status = true;
    private String telefone;

    @Builder.Default
    @OneToMany(mappedBy = "id")
    private List<Device> dispositivos = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    private RolesUser role;
    @CreatedDate
    private LocalDate dataDeCriacao;
    @LastModifiedDate
    private LocalDate dataDeAtulizacao;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public @Nullable String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

}