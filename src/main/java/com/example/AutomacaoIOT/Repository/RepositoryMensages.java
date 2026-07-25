package com.example.AutomacaoIOT.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AutomacaoIOT.Model.ModelDevice.Device;
import com.example.AutomacaoIOT.Model.ModelSaveMensage.SaveMensage;


public interface RepositoryMensages extends JpaRepository<SaveMensage,Long> {
    List<SaveMensage> findByDevice(Device device);
    List<SaveMensage> findTop50ByDeviceOrderByIdDesc(Device device);
    List<SaveMensage> findFirst100ByOrderByIdDesc();
    List<SaveMensage> findTop50ByTopcOrderByIdDesc(String topc);
}
