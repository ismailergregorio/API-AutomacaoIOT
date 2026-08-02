package com.example.AutomacaoIOT.Mapper;

import com.example.AutomacaoIOT.DTO.KeyDevice.DTOKeyDeviceGet;
import com.example.AutomacaoIOT.Model.ModelKeyDevices.ModelKeyDevices;

public class MapperKeyDevices {
    static public DTOKeyDeviceGet toDTOKeyDeviceGet(ModelKeyDevices keyDevices) {
        return new DTOKeyDeviceGet(
                keyDevices.getName(),
                keyDevices.getKey(),
                keyDevices.getStatus(),
                keyDevices.getDevice().getDeviceId());
    }
}
