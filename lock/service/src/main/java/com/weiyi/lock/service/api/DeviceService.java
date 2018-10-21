package com.weiyi.lock.service.api;

import com.weiyi.lock.service.dto.DeviceDTO;
import com.weiyi.lock.service.dto.DeviceListDTO;

import java.util.List;

public interface DeviceService
{
    void addDevice(DeviceDTO deviceDTO);

    int queryCountByDeviceNum(Long deviceNum);

    DeviceDTO queryDeviceByDeviceNum(Long deviceNum);

    List<DeviceDTO> queryDeviceList(DeviceListDTO deviceListDTO);

    List<DeviceDTO> userQueryDeviceList(Long userPhone);

    void updateDevice(DeviceDTO deviceDTO);

    void deleteDevice(Long deviceNum);

    void updateOwner(Long deviceNum,Long userPhone);
}
