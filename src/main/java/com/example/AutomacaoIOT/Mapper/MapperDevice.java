package com.example.AutomacaoIOT.Mapper;

import com.example.AutomacaoIOT.DTO.Device.DtoRespostaDevices;
import com.example.AutomacaoIOT.DTO.Device.DtoRespostaDevicesSemDashbord;
import com.example.AutomacaoIOT.Model.ModelDevice.Device;

public class MapperDevice {
    public static DtoRespostaDevices toDTO(Device device) {
        return new DtoRespostaDevices(
                device.getId(),
                device.getDeviceId(),
                device.getNome(),
                device.getDescricao(),
                device.getPlaca(),
                device.getWidgets().stream().map(MapperWidget::toDTO).toList());
    }

    public static DtoRespostaDevicesSemDashbord toDTO2(Device device) {
        return new DtoRespostaDevicesSemDashbord(
                device.getId(),
                device.getDeviceId(),
                device.getNome(),
                device.getDescricao(),
                device.getPlaca());
    }
}
