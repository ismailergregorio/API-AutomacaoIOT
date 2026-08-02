package com.example.AutomacaoIOT.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AutomacaoIOT.Model.ModelKeyDevices.ModelKeyDevices;

public interface RepositoryKeyDevices extends JpaRepository<ModelKeyDevices, Long> {
    boolean existsByKey(String key);
    Optional<ModelKeyDevices> findByKey(String key);
}
