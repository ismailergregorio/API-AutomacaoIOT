package com.example.AutomacaoIOT.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AutomacaoIOT.Model.ModelDevice.Device;
import com.example.AutomacaoIOT.Model.ModelUser.ModelUser;

import java.util.List;


public interface RepositoryDevice extends JpaRepository<Device,Long> {
    Optional<Device> findByDeviceId(String deviceId);
    Optional<Device> findByDeviceIdAndUsusario(String deviceId, ModelUser ususario);
    List<Device> findByUsusario(ModelUser ususario);
}
