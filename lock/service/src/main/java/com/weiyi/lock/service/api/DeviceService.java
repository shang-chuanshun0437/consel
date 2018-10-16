package com.weiyi.lock.service.api;

import com.weiyi.lock.dao.entity.Device;
import com.weiyi.lock.service.domain.DeviceDTO;
import com.weiyi.lock.service.domain.UserDTO;

public interface DeviceService
{
    void addDevice(DeviceDTO deviceDTO);

    int queryCountByDeviceNum(Long deviceNum);

    DeviceDTO queryDeviceByDeviceNum(Long deviceNum);

    void updateDevice(DeviceDTO deviceDTO);
}
